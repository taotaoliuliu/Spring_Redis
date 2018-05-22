package com.zuidaima;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.Tuple;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class ShardedJedisSentinelPoolSpringTest {
	
	
	
	@Test
	public void testY(){
		
		
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("redis.xml");

		JedisCluster jedisCluster =(JedisCluster) ac.getBean("jedisCluster");
	
			Mem member =new Mem();
			
			member.setAddress("beijing");
		
			member.setName("liusl");
			long time = new Date().getTime();
	

		String memid = UUID.randomUUID().toString();

		jedisCluster.zadd("mem_ids", time, memid);
		
		
		Set<Tuple> zrangeByScoreWithScores = jedisCluster.zrangeWithScores("mem_ids", 0, -1);
	
		Iterator<Tuple> iterator = zrangeByScoreWithScores.iterator();
		
		
		while(iterator.hasNext())
			
		{
			Tuple next = iterator.next();
			
			String uuid = next.getElement();
			
		
			
			System.out.println(uuid);
		}	
	
		
		try {
			jedisCluster.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(jedisCluster);
		
		
		
	} 
	
	@Test
	public void testZ() throws Exception {
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("redis.xml");

		JedisCluster jedisCluster =(JedisCluster) ac.getBean("jedisCluster");
		
		//Long lpush = jedisCluster.lpush("listzzzz","zhangsan","lisi");
		
		Long rpush = jedisCluster.rpush("listzzzz", "wangwu");
		
		
		//jedisCluster.lpush(key, string);
		
		
		//jedisCluster.lrange(key, start, end);
		
		System.err.println(rpush);
		
		jedisCluster.close();
		
		
		
		
	}
	
	
	
	

	public void testX() throws Exception {

		ApplicationContext ac = new ClassPathXmlApplicationContext("redis.xml");
		ShardedJedisPool pool = (ShardedJedisPool) ac.getBean("shardedJedisPool");

		ShardedJedis j = null;
		for (int i = 0; i < 1; i++) {
			try {
				j = pool.getResource();
			    j.set("KEY:" + i, "" + i);
			    
			 
			    System.out.print(i);
			    System.out.print(" ");
			    Thread.sleep(500);
			    pool.returnResource(j);
			} catch (JedisConnectionException e) {
				
				e.printStackTrace();
			//	System.out.print("x");
				i--;
				Thread.sleep(1000);
			}
		}
		

	  
		pool.destroy();
  	}
}