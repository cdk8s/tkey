package com.cdk8s.tkey.server.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 限制 key 只能为 String
 *
 * @param <String>
 * @param <V>
 */
@Service
public class StringRedisService<String, V> {

	@Autowired
	private RedisTemplate redisTemplate;

	//=====================================common start=====================================


	public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
		//设置过期时间
		return redisTemplate.expire(key, timeout, timeUnit);
	}


	public Boolean delete(String key) {
		return redisTemplate.delete(key);
	}

	//=====================================common end=====================================

	//=====================================key value start=====================================

	public void set(String key, V value) {
		redisTemplate.opsForValue().set(key, value);
	}


	public void set(String key, V value, long timeout) {
		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}


	public void set(String key, V value, long timeout, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
	}


	public V get(String key) {
		ValueOperations<String, V> valueOperations = redisTemplate.opsForValue();
		return valueOperations.get(key);
	}


	//=====================================key value  end=====================================


}
