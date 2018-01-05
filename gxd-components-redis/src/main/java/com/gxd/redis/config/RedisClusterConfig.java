//package com.gxd.redis.config;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.Assert;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//
//import javax.annotation.Resource;
//import javax.inject.Singleton;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// * @Author:gxd
// * @Description:
// * @Date: 15:07 2018/1/5
// * @Modified By:
// */
//@Configuration
//@ConditionalOnClass(RedisClusterConfig.class)
//@EnableConfigurationProperties(RedisClusterProperties.class)
//public class RedisClusterConfig {
//
//    @Resource
//    private RedisClusterProperties redisClusterProperties;
//
//    @Bean
//    @Singleton
//    public JedisCluster redisCluster(){
//        Set<HostAndPort> nodes = new HashSet<>();
//        for (String node:redisClusterProperties.getNodes()){
//            String[] parts= StringUtils.split(node,":");
//            Assert.state(parts.length==2, "redis node shoule be defined as 'host:port', not '" + Arrays.toString(parts) + "'");
//            nodes.add(new HostAndPort(parts[0], Integer.valueOf(parts[1])));
//        }
//        return new JedisCluster(nodes);
//    }
//
//}