package com.gxd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GxdComponentsRedisApplicationTests {
	private static final Logger log = LoggerFactory.getLogger(GxdComponentsRedisApplicationTests.class);

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	//@Autowired
	//private RedisUtils redisUtils;

	@Test
	public void contextLoads() {
	}

	@Test
	public void redisTest(){
		String key = "redisTestKey";
		String value = "I am test value";

		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();

		//数据插入测试：
		opsForValue.set(key, value);
		String valueFromRedis = opsForValue.get(key);
		log.info("redis value after set: {}", valueFromRedis);
		assertThat(valueFromRedis, is(value));

		//数据删除测试：
		redisTemplate.delete(key);
		valueFromRedis = opsForValue.get(key);
		log.info("redis value after delete: {}", valueFromRedis);
		assertThat(valueFromRedis, equalTo(null));
	}

	@Test
	public void testSet(){
		//redisUtils.set("test","gongjing");
	}

	@Test
	public void testLpop(){
		Map map = new HashMap();
		map.put("test1","gongjing1");
		//redisService.lpush("list",map);
	}

}
