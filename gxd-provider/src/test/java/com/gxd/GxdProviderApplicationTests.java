package com.gxd;

import com.gxd.mapper.TTestMapper;
import com.gxd.common.model.TTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GxdProviderApplication.class)
public class GxdProviderApplicationTests {

	@Autowired
	private TTestMapper tTestMapper;
	@Test
	public void contextLoads() {
	}

	@Test
	public void insertTest(){
		TTest tTest = new TTest();
		tTest.setAddress("梧桐苑");
		tTest.setCreateBy(1L);
		tTest.setCreateTime(new Date());
		tTest.setName("test");
		tTest.setState(Boolean.TRUE);
		tTestMapper.insert(tTest);
	}

}
