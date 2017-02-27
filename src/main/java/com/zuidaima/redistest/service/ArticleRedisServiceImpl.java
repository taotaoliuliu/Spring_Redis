package com.zuidaima.redistest.service;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.zuidaima.redistest.RedisHelper;
import com.zuidaima.redistest.vo.ArticleModel;
import com.zuidaima.redistest.vo.CommentModel;
import com.zuidaima.redistest.vo.Pager;

import redis.clients.jedis.Jedis;
@Service
@Primary
public class ArticleRedisServiceImpl implements ArticleRedisService{

	@Override
	public Pager getAll(Pager wm, int pageCurrent) {
		
		
		
		Jedis jedis = RedisHelper.getPool().getResource();
		
		
		//5 

		Set<String> zrevrange = jedis.zrevrange("articleuuids", (pageCurrent-1)*wm.getPageShow(), pageCurrent*wm.getPageShow()-1);
		
		
		Long zcard = jedis.zcard("articleuuids");
		
		wm.setTotalNum(zcard.intValue());
		
		
		List<ArticleModel> list =new ArrayList<>();
		
		
		for(String uuid:zrevrange){
			
			
			Map<String, String> hgetAll = jedis.hgetAll("arrticleH:"+uuid);
			
			
			
			
			String title = hgetAll.get("title");
			String content = hgetAll.get("content");
			String createTime = hgetAll.get("createtime");
			String authoruuid = hgetAll.get("authoruuid");
			ArticleModel m =new ArticleModel();
			
			m.setTitle(title);
			
			m.setContent(content);
			
			m.setCreateOpeTime(createTime);
			
			m.setUuid(uuid);
			
			
			
			list.add(m);
			
		
		}
		
		wm.setResults(list);
		
		return wm;
	}

	@Override
	public void add(ArticleModel m) {
		
		String uuid = m.genUuid();
	
		Jedis jedis = RedisHelper.getPool().getResource();
		
		Map<String,String> map =new HashMap<>();
		
		map.put("title", m.getTitle());
		
		
		map.put("uuid", uuid);
		
		map.put("source", m.getSource());

		
		map.put("content", m.getContent());
		
		map.put("createtime", m.getCreateOpeTime());
				
		jedis.hmset("arrticleH:"+uuid, map);
		
		long time = new Date().getTime();
		
		
		jedis.zadd("articleuuids", time, uuid);
		
		
		
		
	}

	@Override
	public ArticleModel getByUuid(String uuid) {
		
		Jedis jedis = RedisHelper.getPool().getResource();
		
		Map<String, String> hgetAll = jedis.hgetAll("arrticleH:"+uuid);
		
		String title = hgetAll.get("title");
		String content = hgetAll.get("content");
		String createTime = hgetAll.get("createtime");
		String authoruuid = hgetAll.get("authoruuid");
		String source = hgetAll.get("source");
		ArticleModel m =new ArticleModel();
		
		m.setTitle(title);
		
		m.setContent(content);
		
		m.setCreateOpeTime(createTime);
		m.setSource(source);
		
		m.setUuid(uuid);
	

		return m;
	}

	@Override
	public Pager getByCondition(ArticleModel qm, Pager pager,int pageit) {
		
		
		
		
		
		
		
		Jedis jedis = RedisHelper.getPool().getResource();
		
		//0 9   10  19
		
		System.out.println((pager.getNowPage()-1)*pager.getPageShow());
		
		System.out.println(pager.getNowPage());
		
		
		System.out.println(pager.getNowPage()*pager.getPageShow()-1);

		Set<String> zrevrange = jedis.zrevrange("articleuuids", (pageit-1)*pager.getPageShow(), pageit*pager.getPageShow()-1);
		
		
		Long zcard = jedis.zcard("articleuuids");
		
		pager.setTotalNum(zcard.intValue());
		
		
		List<ArticleModel> list =new ArrayList<>();
		
		
		for(String uuid:zrevrange){
			
			
			Map<String, String> hgetAll = jedis.hgetAll("arrticleH:"+uuid);
			

			
			String title = hgetAll.get("title");
			String content = hgetAll.get("content");
			String createTime = hgetAll.get("createtime");
			String authoruuid = hgetAll.get("authoruuid");
			

			ArticleModel m =new ArticleModel();
			
			m.setTitle(title);
			
			m.setContent(content);
			
			m.setCreateOpeTime(createTime);
			
			String source = hgetAll.get("source");
			
			m.setSource(source);
			m.setUuid(uuid);


			
			
			list.add(m);
			
		
		}
		
		pager.setResults(list);
	
		
		return pager;
	}

	@Override
	public void delete(ArticleModel byUuid) {
		
		Jedis jedis = RedisHelper.getPool().getResource();
		
		
		jedis.hdel("arrticleH:"+byUuid.getUuid());
		
		
		jedis.zrem("articleuuids", byUuid.getUuid());
		
	}

	@Override
	public void update(ArticleModel m) {
		
	}

	@Override
	public void saveComment(String articleUuid, String content) {
		Jedis jedis = RedisHelper.getPool().getResource();
		
		CommentModel cm =new CommentModel();
		
		
		String genUuid = cm.genUuid();
		
		Map<String,String> map =new HashMap<>();
		
		map.put("content", 	content);
		
		map.put("uuid", genUuid);
		
		jedis.hmset("commentH:"+genUuid, map);
		
		
		jedis.lpush("article:"+articleUuid+":comment", genUuid);
		
		
		
		
	}

	@Override
	public List<CommentModel> getArticleComment(String uuid) {
		
		List<CommentModel> list=new ArrayList<>();
		
		
		Jedis jedis = RedisHelper.getPool().getResource();
		List<String> lrange = jedis.lrange("article:"+uuid+":comment", 0, -1);
		
		
		for(String str: lrange){
			
			Map<String, String> hgetAll = jedis.hgetAll("commentH:"+str);
			
			CommentModel cm =new CommentModel();
			
			cm.setUuid(str);
			
			cm.setContent(hgetAll.get("content"));

			list.add(cm);
			
			
		}
		
		return list;
	}



}