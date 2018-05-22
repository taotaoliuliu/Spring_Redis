package com.zuidaima;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

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
