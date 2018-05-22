package com.zuidaima;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSONObject;

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
	
	
	public void test(){
		
GenericObjectPoolConfig poolConfig=new GenericObjectPoolConfig();
		
		poolConfig.setMaxTotal(300);
		
		poolConfig.setMaxIdle(10);;
		
		
		JedisPool pool =new JedisPool(poolConfig, "192.168.137.82", 6379);
		
		Jedis jedis = pool.getResource();
		
		//jedis.scr
		
	}
	
	
	@Test
	public void testCall() throws InterruptedException{
        Jedis jedis = new Jedis(host); 
        jedis.flushAll();

		
		generateTestData();
		
		while (true){

		String sc="local hongBao = redis.call('rpop', KEYS[1]);\n"

		 + "print('hongBao:', hongBao);\n";  
		

        Object object = jedis.eval(sc, 1, hongBaoList);
    	System.out.println("红包个数："+jedis.llen(hongBaoList));  

        System.out.println(object);
		}
        

	}
	
	
	/**
	 * lua 抢红包
	 */
	@Test
	public void testqiangHongBao(){
		
		
		try {
			generateTestData();
			//testTryGetHongBao();  

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
		
	}
	
	
	
	 	static String host = "192.168.137.87";  
	    static int honBaoCount = 1000;  
	      
	    static int threadCount = 20;  
	      
	    static String hongBaoList = "hongBaoList";  
	    static String hongBaoConsumedList = "hongBaoConsumedList";  
	    static String hongBaoConsumedMap = "hongBaoConsumedMap";  
	
	public void generateTestData() throws InterruptedException {  
	        Jedis jedis = new Jedis(host);  
	        jedis.flushAll();  
	        final CountDownLatch latch = new CountDownLatch(threadCount);  
	        for(int i = 0; i < threadCount; ++i) {  
	            final int temp = i;  
	            Thread thread = new Thread() {  
	                public void run() {  
	                    Jedis jedis = new Jedis(host);
	                    int per = honBaoCount/threadCount;  
	                    JSONObject object = new JSONObject();  
	                    for(int j = temp * per; j < (temp+1) * per; j++) {  
	                        object.put("id", j);  
	                        object.put("money", j);  
	                        jedis.lpush(hongBaoList, object.toJSONString());  
	                        //{} list<> list<id==33,money=xx> <id==44.money=33>
	                    }  
	                    latch.countDown();  
	                }  
	            };  
	            thread.start();  
	        }  
	        latch.await();  
	    }  
	      
	public void testTryGetHongBao() throws InterruptedException {  
		StopWatch watch = new StopWatch(); 
	//  -- 函数：尝试获得红包，如果成功，则返回json字符串，如果不成功，则返回空  
	//  -- 参数：红包队列名， 已消费的队列名，去重的Map名，用户ID  
	//  -- 返回值：nil 或者 json字符串，包含用户ID：userId，红包ID：id，红包金额：money  
	     String tryGetHongBaoScript =   
//	          "local bConsumed = redis.call('hexists', KEYS[3], KEYS[4]);\n"  
//	          + "print('bConsumed:' ,bConsumed);\n"  
	            "if redis.call('hexists', KEYS[3], KEYS[4]) ~= 0 then\n"  
	            + "return nil\n"  
	            + "else\n"  
	            + "local hongBao = redis.call('rpop', KEYS[1]);\n"  // <id=xx,money=xx>
//	          + "print('hongBao:', hongBao);\n"  
	            + "if hongBao then\n"  
	            + "local x = cjson.decode(hongBao);\n"  
	            + "x['userId'] = KEYS[4];\n"  
	            + "local re = cjson.encode(x);\n"  
	            + "redis.call('hset', KEYS[3], KEYS[4], KEYS[4]);\n"  // HSET myhash field1 "foo" 
	            + "redis.call('lpush', KEYS[2], re);\n"  //<usrId=xx,id=xx,money=xxx>
	            + "return re;\n"  
	            + "end\n"  
	            + "end\n"  
	            + "return nil";  
	        final CountDownLatch latch = new CountDownLatch(threadCount);  
	        System.err.println("start:" + System.currentTimeMillis()/1000);  
	        watch.start();  
	        for(int i = 0; i < threadCount; ++i) {  
	            final int temp = i;  
	            Thread thread = new Thread() {  
	                public void run() {  
	                    Jedis jedis = new Jedis(host);  
	                    String sha = jedis.scriptLoad(tryGetHongBaoScript);  
	                    int j = honBaoCount/threadCount * temp;  
	                    while(true) {  
	                        Object object = jedis.eval(tryGetHongBaoScript, 4, hongBaoList, hongBaoConsumedList, hongBaoConsumedMap, "" + j);  
	                        j++;  
                        	System.out.println("红包个数："+jedis.llen(hongBaoList));  

	                        if (object != null) {  
	                        	System.out.println("get hongBao:" + object);  
	                        }else {  
	                            //已经取完了  
	                            if(jedis.llen(hongBaoList) == 0)  
	                                break;  
	                        }  
	                    }  
	                    latch.countDown();  
	                }  
	            };  
	            thread.start();  
	        }  
	          
	        latch.await();  
	        watch.stop();  
	          
	        System.err.println("time:" + watch.getTotalTimeSeconds());  
	        System.err.println("speed:" + honBaoCount/watch.getTotalTimeSeconds());  
	        System.err.println("end:" + System.currentTimeMillis()/1000);  
	    }  
	
	
	
	
	
	
	
	
	
	  

	
	public void testLuaScript() throws LuaException {  
		
		
		String maxAverage="function maxAverage(...)     "  
	            +"agr={...}                 "  
	            +"max=0                     "  
	            +"sum=0                     "  
	            +"for k,v in ipairs(agr) do "  
	            +"print(k,v)                "  
	            +"sum=sum+v                 "  
	            +"if v> max then            "  
	            +"max=v                     "  
	            +"end                       "  
	            +"end                       "  
	            +"return max,sum/#agr       "  
	            +"end;                      ";  
		
		
	    LuaState L = LuaStateFactory.newLuaState();  
	    L.openLibs();   
	    System.out.println("这里是Java程序调用Lua脚本");   
	    // 加载脚本  
	    L.LdoString(maxAverage);//获取Lua全局函数,java_lua_f    
	    LuaObject func = L.getLuaObject("maxAverage");    
	    //因为LuaObject类映射了Lua语言中所有类型,即LuaObject也是Lua函数    
	    //通过java中调用LuaObject.call()函数,可以使用lua函数。    
	    //该函数可以传递多个参数 或者返回多个参数    
	    Object[] teste = func.call(new Object[] { 2,4,98,12,34,56},2);    
	        System.out.println(teste[0]);    
	    System.out.println(teste[1]);  
	    System.out.println("这里是Java程序调用Lua脚本end");   
	 }  
  

	    /** 
	     * @param args 
	     */  
	    public static void main(String[] args) {  
	          
	        LuaState L = LuaStateFactory.newLuaState();  
	        // 加载lua标准库,否则一些lua基本函数无法使用  
	        L.openLibs();  
	        // doFile  
	        L.LdoFile("res/test01.lua");  
	          
	        //---------------------------------------------值传递测试  
	        // 找到函数 sum  
	        L.getField(LuaState.LUA_GLOBALSINDEX, "sum");  
	          
	        // 参数1压栈  
	        L.pushNumber(100);  
	          
	        // 参数2压栈  
	        L.pushNumber(50);  
	          
	        // 调用，共2个参数1个返回值  
	        L.call(2, 1);  
	          
	        // 保存返回值到result中  
	        L.setField(LuaState.LUA_GLOBALSINDEX, "result");  
	          
	        // 读入result  
	        LuaObject lobj = L.getLuaObject("result");  
	        // 打印结果   
	        System.out.println(lobj.getNumber());  
	          
	        //---------------------------------------------对象传递测试  
	        Value vaaa = new Value();  
	          
	        L.getField(LuaState.LUA_GLOBALSINDEX, "test1");  
	          
	        try {  
	            L.pushObjectValue(vaaa);  
	        } catch (LuaException e) {  
	            e.printStackTrace();  
	        }  
	          
	        L.call(1, 0);  
	          
	        vaaa.print();  
	    }  
	  
	  

	
}


