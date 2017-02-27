package com.zuidaima;

import java.util.Iterator;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

public class TestSpringRedisCluster {

	/**
	 * spring cluster集群模式测试
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		
		
		
		
		ApplicationContext ctx=new	ClassPathXmlApplicationContext("applicationContext-redis-cluster.xml");
		
		
		RedisTemplate redisTemplate=	(RedisTemplate) ctx.getBean("redisTemplate");
		
		
		//redisTemplate.c
	
		
		ZSetOperations opsForZSet = redisTemplate.opsForZSet();
		
		Set<TypedTuple<String>> rangeWithScores = opsForZSet.rangeWithScores("mem_ids", 0, -1);
		
		
	//	Set<Tuple> zrangeByScoreWithScores = opsForZSet.zrangeWithScores("mem_ids", 0, -1);
	
		Iterator<TypedTuple<String>> iterator = rangeWithScores.iterator();
		
		
		while(iterator.hasNext())
			
		{
			TypedTuple next = iterator.next();
			
			String uuid = next.getValue().toString();
			
		
			
			System.out.println(uuid);
		}	
	
	
		
		
		Set<String> keys = redisTemplate.keys("*");
		
		
for(String k:keys){
			
			System.out.println(k);
			
		}
		
	

		
		
	}
		

}
