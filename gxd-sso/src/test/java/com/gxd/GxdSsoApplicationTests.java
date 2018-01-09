package com.gxd;

import com.gxd.redis.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class GxdSsoApplicationTests {
	@Autowired
	private RedisUtils redisUtils;
	@Test
	public void get() {
		String hellokoding = redisUtils.get("hellokoding");
		System.out.println("get:"+hellokoding);
	}


}
