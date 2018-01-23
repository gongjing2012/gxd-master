//package com.gxd.redis.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//
//import java.lang.reflect.Method;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * @Author:gxd
// * @Description:
// * @Date: 11:26 2018/1/8
// * @Modified By:
// */
//@Configuration
//@RefreshScope
//@Component("jedisClusterConfig")
//public class JedisClusterConfig {
//	private final Logger log = LoggerFactory.getLogger(this.getClass());
//	private static JedisCluster jedisCluster = null;
//	@Autowired
//    private RedisClusterProperties redisProperties;
//    /**
//     * 注意：
//     * 这里返回的JedisCluster是单例的，并且可以直接注入到其他类中去使用
//     * @return
//     */
//    @Bean(name="jedisClusterInit")
//	//@Lazy(true)
//    public synchronized JedisCluster createJedisCluster() {
//        List<String> serverArray = redisProperties.getNodes();//获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
//        Set<HostAndPort> nodes = new HashSet<>();
//		log.info("获取服务器地址成功 - timeout: {}, nodes: {}, maxAttempts: {}",
//				redisProperties.getTimeout(), redisProperties.getNodes(), redisProperties.getMaxAttempts());
//        for (String ipPort : serverArray) {
//            String[] ipPortPair = ipPort.split(":");
//            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
//        }
//		// 只有当jedisCluster为空时才实例化
//		if (jedisCluster == null) {
//			jedisCluster = new JedisCluster(nodes, redisProperties.getTimeout(),redisProperties.getMaxAttempts());
//		}
//		return jedisCluster;
//    }
//
//    @Bean
//	public KeyGenerator wiselyKeyGenerator(){
//		return new KeyGenerator() {
//			@Override
//			public Object generate(Object target, Method method, Object... params) {
//				StringBuilder sb = new StringBuilder();
//				sb.append(target.getClass().getName());
//				sb.append(method.getName());
//				for (Object obj : params) {
//					sb.append(obj.toString());
//				}
//				return sb.toString();
//			}
//		};
//	}
//	//缓存管理器
//	@Bean
//	public CacheManager cacheManager(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
//		RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
//		//设置缓存过期时间
//		cacheManager.setDefaultExpiration(10000);
//		return cacheManager;
//	}
//}
