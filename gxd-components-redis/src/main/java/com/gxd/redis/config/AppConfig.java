//package com.gxd.redis.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//
///**
// * @Author:gxd
// * @Description:
// * @Date: 13:23 2018/1/19
// * @Modified By:
// */
//@Configuration
//public class AppConfig {
//    private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);
//
//    /**
//     * Type safe representation of application.properties
//     */
//    @Autowired
//    private ClusterConfigurationProperties clusterProperties;
//
//    @Bean(name="redisConnection")
//    @Lazy(true)
//    public RedisConnectionFactory connectionFactory() {
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterProperties.getNodes());
//        redisClusterConfiguration.setMaxRedirects(clusterProperties.getMaxRedirects());
//        logger.info("load redis cluster success {} ", clusterProperties.getNodes().toString());
//        return new JedisConnectionFactory(redisClusterConfiguration);
//    }
//}
