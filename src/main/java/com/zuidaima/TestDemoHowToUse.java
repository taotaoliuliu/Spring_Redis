package com.zuidaima;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

public class TestDemoHowToUse {
	
	private final static  String ZSET_KEY="myzset";
	
	private final static  String LIST_KEY="mylist";

	
	
	private RedisTemplate redisTemplate;
	

	
	/**
	 * 
	 * redis zset  热词 top N
	 */
	public static void main(String[] args) {
		
	ApplicationContext ctx=new	ClassPathXmlApplicationContext("applicationContext.xml");
	
	
	RedisTemplate redisTemplate=	(RedisTemplate) ctx.getBean("redisTemplate");
	
	ZSetOperations opsForZSet = redisTemplate.opsForZSet();
	
	opsForZSet.add(ZSET_KEY, "james", 1);
	
	
	opsForZSet.incrementScore("myzset", "kobe", 1);
	
	//opsForZSet.remove(KEY, "james");
	
	Long zCard = opsForZSet.zCard(ZSET_KEY);
	
	System.out.println(zCard);
	
	Set<TypedTuple<String>> reverseRangeByScoreWithScores = opsForZSet.reverseRangeWithScores("myzset", 0, -1);
	
	
	Iterator<TypedTuple<String>> iterator = reverseRangeByScoreWithScores.iterator();
	
	while(iterator.hasNext()){
		
		TypedTuple<String> next = iterator.next();
		
		
		System.out.println(next.getValue()+"########"+next.getScore());
		
		
		
	}
	
		
		
		
	}
	
	@Before
	public void testBefore(){
		
		ApplicationContext ctx=new	ClassPathXmlApplicationContext("applicationContext.xml");
		
		
		redisTemplate=	(RedisTemplate) ctx.getBean("redisTemplate");
		
	}
	
	
	
	/**
	 * 
	 * redis list  最新的几条数据  ltrim 最新的5000条评论：
	 */
	@Test
	public void testListSize(){
		
		ListOperations opsForList = redisTemplate.opsForList();
		
		
		
		Long size = opsForList.size(LIST_KEY+"1111");
		
		System.out.println(size);
		//opsForList.trim(LIST_KEY+"1111", 0, 3);

		
		
		/*for(int i=0;i<8;i++){
			
			opsForList.leftPush(LIST_KEY+"1111", "wwww"+i);
		}*/
		

/*if(size>5){
			
			opsForList.rightPop(LIST_KEY+"111");
			
			
		}*/

List ranges = opsForList.range(LIST_KEY+"1111", 0, 1000);
		
		for(Object list :ranges){
			
			System.out.println(list.toString());
			
		}
		

		
	}
	/**
	 * 
	 * redis list
	 */
	
	@Test
	public void testList(){
		
		
		ListOperations opsForList = redisTemplate.opsForList();
		
	
	
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
