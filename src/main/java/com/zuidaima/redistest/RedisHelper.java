package com.zuidaima.redistest;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisPool;

public class RedisHelper {
	
	
	private static JedisPool pool=null;
	
	public static JedisPool getPool(){
		
		if(pool==null){
			

			GenericObjectPoolConfig poolConfig=new GenericObjectPoolConfig();
			
			poolConfig.setMaxTotal(300);
			
			poolConfig.setMaxIdle(10);;
			
			
			pool =new JedisPool(poolConfig, "192.168.137.82", 6379);
			
			
			
		}
		
		return pool;
		
		
		
	}

}
