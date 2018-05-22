package com.zuidaima;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.chainsaw.Main;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

public class TestRedisTemplateLua {

	
	public static void main(String[] args) {
		
		
		
		ApplicationContext ctx=new	ClassPathXmlApplicationContext("applicationContext.xml");
		
		
		RedisTemplate redisTemplate=	(RedisTemplate) ctx.getBean("redisTemplate");
		
		List<String> keys = new ArrayList<String>();
		RedisScript<Long> script = new DefaultRedisScript<Long>(
				"local size = redis.call('dbsize'); return size;", Long.class);
			Object dbsize = redisTemplate.execute(script, keys, new Object[] {});
		System.out.println("sha1:" + script.getSha1());
		System.out.println("Lua:" + script.getScriptAsString());
		System.out.println("dbsize:" + dbsize);
	
		String script2="local times=redis.call('incr',KEYS[1])\n"
				+"if times == 1 then \n"
				+"redis.call('expire',KEYS[1],ARGV[1])\n"
				+"end\n"
				+"if times > tonumber(ARGV[2]) then\n"
				+"return 0\n"
				+"end\n"
				+"return 1"
				;
		
		List<String> keys2 =new ArrayList<String>();
		
		
		keys2.add("testkey2www");
		
		
		DefaultRedisScript<Long> aa =new DefaultRedisScript<>(script2,Long.class);
		
		//aa.setResultType(Long.class);
				
		
		Object execute = redisTemplate.execute(aa, keys2, "10","3");
		
		//redisTemplate.execute
		
		System.out.println(execute);
		
		
	}
	
	
	@Test
	public void test72() {
		
		ApplicationContext ctx=new	ClassPathXmlApplicationContext("applicationContext.xml");
		RedisTemplate redisTemplate=	(RedisTemplate) ctx.getBean("redisTemplate");
		DefaultRedisScript<Long> script = new DefaultRedisScript<Long>();
		/**
		 * isexistskey.lua内容如下：
		 * 
		 * return tonumber(redis.call("exists",KEYS[1])) == 1;
		 */
		script.setScriptSource(new ResourceScriptSource(new ClassPathResource(
				"/isexistskey.lua")));

		script.setResultType(Long.class);// Must Set

		System.out.println("script:" + script.getScriptAsString());
		Object isExist = redisTemplate.execute(script,
				Collections.singletonList("k2222"), new Object[] {"10","3"});
			System.out.println(isExist+"isExist");

	
	}
	
	
}
