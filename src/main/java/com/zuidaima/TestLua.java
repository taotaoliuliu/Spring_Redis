package com.zuidaima;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestLua {
	
	
	@Test
	public void testJedis() throws InterruptedException{
		
		
GenericObjectPoolConfig poolConfig=new GenericObjectPoolConfig();
		
		poolConfig.setMaxTotal(300);
		
		poolConfig.setMaxIdle(10);;
		
		
		JedisPool pool =new JedisPool(poolConfig, "192.168.137.82", 6379);
		
		Jedis jedis = pool.getResource();
		
		
		//jedis.z
		
		
	}

	
	
	/**
	 * 
	 * jedis 测试   lua 脚本
	 * @throws InterruptedException
	 */
	
	@Test
	public void testJedisPool() throws InterruptedException{
		
		
		GenericObjectPoolConfig poolConfig=new GenericObjectPoolConfig();
		
		poolConfig.setMaxTotal(300);
		
		poolConfig.setMaxIdle(10);;
		
		
		JedisPool pool =new JedisPool(poolConfig, "192.168.137.82", 6379);
		
		Jedis jedis = pool.getResource();
		
		List<String> keys =new ArrayList<>();
	
		List<String> args =new ArrayList<>();
	
		//Object evalsha = jedis.evalsha("", keys, args);
		
		
		String script="local times=redis.call('incr',KEYS[1])\n"
				+"if times == 1 then \n"
				+"redis.call('expire',KEYS[1],ARGV[1])\n"
				+"end\n"
				+"if times > tonumber(ARGV[2]) then\n"
				+"return 0\n"
				+"end\n"
				+"return 1"
				;
		
		Object eval = jedis.eval(script, 1,"testkey","20","3");
		
		System.out.println(eval);

	}
}
