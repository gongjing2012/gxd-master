package com.gxd;

import com.gxd.rocketmq.message.MessageProcessorImplTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GxdComponentsRocketmqApplicationTests {

	@Autowired
	private MessageProcessorImplTest messageProcessorImplTest;
	@Test
	public void contextLoads() {
	}
}
