package com.jt.servive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
//@Lazy// 表示当前类懒加载
public class RedisService {
	/**
	 * 1.表示程序启动时必须注入该对象
	 *  如果该注入操作很有可能
	 */
	@Autowired(required = false) //表示程序启动时必须要注入对象
	private JedisSentinelPool pool;
	
	public void setex(String key,String value,Integer seconds) {
		Jedis jedis =pool.getResource();
		jedis.setex(key, seconds, value);
		jedis.close();
		
	}
     public String get(String key) {
    	
    	 Jedis jedis=pool.getResource();
    	 String value = jedis.get(key);
    	 jedis.close();
    	 return value;
    	 
     }
}
