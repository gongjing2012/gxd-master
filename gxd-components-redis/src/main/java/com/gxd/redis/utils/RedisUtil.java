//package com.gxd.redis.utils;
//
//import com.gxd.common.constants.Constants;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.*;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeSet;
//
//@Component
//public class RedisUtil {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//	@Autowired
//	private JedisCluster jedisCluster;
//
//	public Set<String> getKeys(String pattern) {
//		Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
//		TreeSet<String> keys = new TreeSet<String>();
//		for (String k : clusterNodes.keySet()) {
//			logger.debug("Getting keys from: {}", k);
//			JedisPool jp = clusterNodes.get(k);
//			Jedis connection = jp.getResource();
//			try {
//				int cursor = 0;
//				do {
//					ScanResult<String> scanResult = connection.scan("" + cursor, new ScanParams().match(pattern));
//					if (scanResult.getResult().size() > 0) {
//						for (String key : scanResult.getResult()) {
//							keys.add(key);
//						}
//					}
//					cursor = Integer.valueOf(new String(scanResult.getCursorAsBytes()));
//				} while (cursor > 0);
//			} catch (Exception e) {
//				logger.error("Getting keys error: {}", e);
//			} finally {
//				logger.debug("Connection closed.");
//				if (connection != null) {
//					connection.close();// 用完一定要close这个链接！！！
//				}
//			}
//		}
//		logger.debug("------------->keys=" + keys.toString());
//		return keys;
//	}
//
//	public static Set<Long> convertStringSet2LongSet(Set<String> strSet) {
//		Set<Long> longSet = new HashSet<Long>(strSet.size());
//
//		for (String str : strSet) {
//			longSet.add(Long.valueOf(str));
//		}
//
//		return longSet;
//	}
//
//	public static int randomSuperTinyExpireSecs() {
//		return Constants.REDIS_EXPIRE_TIME_ST + (int) (Math.random() * 100);
//	}
//	public static int randomTinyExpireSecs() {
//		return Constants.REDIS_EXPIRE_TIME_T + (int) (Math.random() * 100);
//	}
//	public static int randomSmallExpireSecs() {
//		return Constants.REDIS_EXPIRE_TIME_S + (int) (Math.random() * 100);
//	}
//	public static int randomMediumExpireSecs() {
//		return Constants.REDIS_EXPIRE_TIME_M + (int) (Math.random() * 100);
//	}
//	public static int randomLargeExpireSecs() {
//		return Constants.REDIS_EXPIRE_TIME_L + (int) (Math.random() * 100);
//	}
//
//}
