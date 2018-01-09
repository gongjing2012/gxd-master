package com.gxd;

import com.gxd.redis.utils.RedisUtils;
import com.gxd.redis.utils.RedissLockUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GxdComponentsRedisApplicationTests {
	private static RedissonClient redissonClient;
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	@Autowired
	private RedisUtils redisUtils;

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
		assertThat(valueFromRedis, is(value));

		//数据删除测试：
		redisTemplate.delete(key);
		valueFromRedis = opsForValue.get(key);
		assertThat(valueFromRedis, equalTo(null));
	}

	@Test
	public void testSet(){
		boolean flag = redisUtils.set("test1","gongjing1");
		System.out.println(flag);
	}
	@Test
	public void testGet(){
		String test = redisUtils.get("test");
		System.out.println(test);
	}
	@Test
	public void testDel(){
		redisUtils.del("test");
		String test = redisUtils.get("test");
		System.out.println(test);
	}

	@Test
	public void testIncr(){
		redisUtils.incr("test",1L);
	}

	@Test
	public void testDecr(){
		redisUtils.decr("test",1L);
	}

	@Test
	public void testExpire(){
		redisUtils.expire("expireKey","expireValue",1200L);
	}

	@Test
	public void testGetExpire(){
		int test  = redisUtils.getExpire("expireKey");
		System.out.println("testGetExpire:"+test);
	}


	@Test
	public void testHasKey(){
		boolean expireKey = redisUtils.hasKey("expireKey");
		System.out.println("hasKey:"+expireKey);
	}

	//@Test
	//public void testsetObject(){
	//	RedisLoginDTO redisLogin = new RedisLoginDTO("testgj", "123", System.currentTimeMillis() + 60L * 1000L * 30L);
	//	redisUtils.setObject("obj", redisLogin, 360000000);
	//}
    //
	//@Test
	//public void testgetObject(){
	//	RedisLoginDTO redisLoginDTO = (RedisLoginDTO) redisUtils.getObject("obj");
	//	System.out.println(redisLoginDTO);
	//}

	@Test
	public void testhmset() {
		Map map = new HashMap();
		map.put("gong", "jing");
		map.put("gong1", "jing1");
		boolean hmset = redisUtils.hmset("testMap", map);
		System.out.println("hmset:" + hmset);
	}
	@Test
	public void testhmsetTime() {
		Map map = new HashMap();
		map.put("gong", "jing");
		map.put("gong1", "jing1");
		boolean testMapTime = redisUtils.hmset("testMapTime", map, 1000);
		System.out.println("testMapTime:" + testMapTime);
	}
	@Test
	public void testhget() {
		Object hget = redisUtils.hget("testMap", "gong");
		System.out.println("hget:" + hget);
	}

	@Test
	public void testhasKey() {
		boolean testMap = redisUtils.hasKey("testMap");

		System.out.println("hasKey:" + testMap);

	}

	@Test
	public void testhset1() {
		redisUtils.hset("testMap", "gong2", "jing2");
	}

	@Test
	public void testhmgettestMap(){
		Map<Object, Object> testMap1 = redisUtils.hmget("testMap");

		System.out.println("hmget:" + testMap1.toString());
	}
	@Test
	public void testhmgettestMap1() {
		redisUtils.hset("testMap", "gong3", 3);
	}

	@Test
	public void testhincr() {
		redisUtils.hincr("testMap", "gong3", 1L);
	}
	@Test
	public void testhdecr() {
		redisUtils.hdecr("testMap", "gong3", 1L);
	}

	@Test
	public  void testHdel(){
		redisUtils.hdel("testMap","gong3");
	}
	@Test
	public void testlSet(){
		List list = new ArrayList();
		Map map = new HashMap();
		map.put("gong", "jing");
		map.put("gong1", "jing1");
		list.add(map);
		redisUtils.lSet("testlist",list);
	}
	@Test
	public void testlGet(){
		List<Object> testlist = redisUtils.lGet("testlist", 0, -1);
		System.out.println("testlGet:"+testlist);
	}

	@Test
	public void testlisttime(){
		List list = new ArrayList();
		Map map = new HashMap();
		map.put("gong", "jing");
		map.put("gong1", "jing1");
		list.add(map);
		redisUtils.lSet("testlisttime",list,1000L);
	}

	@Test
	public void testlGetListSize(){
		long testlisttime = redisUtils.lGetListSize("testlist");
		System.out.println("lGetListSize:"+testlisttime);
	}

	@Test
	public void testllSet(){
		List list = new ArrayList();
		Map map = new HashMap();
		map.put("gong", "jing");
		map.put("gong1", "jing1");
		list.add(map);
		redisUtils.lSet("testlistlist",list);
	}
	@Test
	public void testllGetIndex(){
		Object testlistlist = redisUtils.lGetIndex("testlistlist", 0L);
		System.out.println("lGetIndex:"+testlistlist.toString());
	}

	@Test
	public void testlllUpdateIndex(){

		List list = new ArrayList();
		Map map = new HashMap();
		map.put("gong3", "jing3");
		map.put("gong2", "jing2");
		list.add(map);

		Object testlistlist = redisUtils.lUpdateIndex("testlistlist", 0L,list);
		System.out.println("lGetIndex:"+testlistlist.toString());
	}

	@Test
	public void testlZadd(){
		redisUtils.zAdd("zaddtest", "test",2d);
	}
	@Test
	public void testlZadd1(){
		redisUtils.zAdd("zaddtest", "test1",3d);
	}

	@Test
	public void testlZadd2(){
		redisUtils.zAdd("zaddtest", "test2",1d);
	}
	@Test
	public void testrangeByScore(){
		Set<Object> zaddtest = redisUtils.rangeByScore("zaddtest", 0d, 1d);
		System.out.println(zaddtest.toArray().toString());
	}

	@Test
	public void testsadd(){
		redisUtils.sadd("testSet","gong");
	}
	@Test
	public void testsadd1(){
		redisUtils.sadd("testSet","gong1");
	}

	@Test
	public void testsGet(){
		Set<Object> testset = redisUtils.sGet("testset");
		System.out.println(testset);
	}

	@Test
	public void testssrename(){
		redisUtils.srename("testset","testSet");
	}

	@Test
	public void testssrem(){
		redisUtils.srem("testSet","gong");
	}

	@Test
	public void testsGetSetSize(){
		long testset = redisUtils.sGetSetSize("testset");
		System.out.println("sGetSetSize:"+testset);
	}

	@Test
	public void testsetRemove(){
		redisUtils.setRemove("testSet","gong1");
	}
	@Test
	public void testsSet(){
		redisUtils.sadd("testSet","gong1");
	}
	public static RedissonClient getRedisson(){
		return redissonClient;
	}
	//static{
	//	Config config = new Config();
	//	config.useSingleServer().setAddress("redis://127.0.0.1:6379").setPassword("123456");
	//	redissonClient = Redisson.create(config);
	//}
	//@Test
	//public void testReentrantLock(){
	//	RLock lock = redissonClient.getLock("anyLock");
	//	try{
	//		// 1. 最常见的使用方法
	//		//lock.lock();
	//		// 2. 支持过期解锁功能,10秒钟以后自动解锁, 无需调用unlock方法手动解锁
	//		//lock.lock(10, TimeUnit.SECONDS);
	//		// 3. 尝试加锁，最多等待3秒，上锁以后10秒自动解锁
	//		boolean res = lock.tryLock(3, 10, TimeUnit.SECONDS);
	//		if(res){ //成功
	//			// do your business
	//		}
	//	} catch (InterruptedException e) {
	//		e.printStackTrace();
	//	} finally {
	//		lock.unlock();
	//	}
	//}

	@Test
	public void testLock(){
		RedissLockUtils.lock("test");

		System.out.println("test");
		RedissLockUtils.unlock("test");
	}

}
