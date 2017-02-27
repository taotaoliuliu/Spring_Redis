package com.zuidaima;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

import redis.clients.jedis.params.sortedset.ZAddParams;

public class TestSentinel {
	
	private final static  String ZSET_KEY="myzset";
	
	private final static  String LIST_KEY="mylist";

	
	
	private RedisTemplate redisTemplate;
	

	
	
	@Before
	public void testBefore(){
		
		ApplicationContext ctx=new	ClassPathXmlApplicationContext("applicationContext-redis-sentinel.xml");
		
		
		redisTemplate=	(RedisTemplate) ctx.getBean("redisTemplate");
		
	}
	/**
	 * 
	 * redis list  spring哨兵集群模式测试
	 */
	
	@Test
	public void testList(){
		
		ListOperations opsForList = redisTemplate.opsForList();
		
		opsForList.leftPush(LIST_KEY, "weide3");

	
		List ranges = opsForList.range(LIST_KEY, 0, 1000);
		
		for(Object list :ranges){
			
			System.out.println(list.toString());
			
		}
	
	}
	
	@Test
	public void testPubSub() throws InterruptedException{
		
		
		Thread.sleep(1000000);
		
	}
	
	
	
	
}
