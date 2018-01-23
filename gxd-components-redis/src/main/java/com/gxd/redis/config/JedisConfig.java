//package com.gxd.redis.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.stereotype.Component;
//
///**
// * @Author:gxd
// * @Description:
// * @Date: 1:13 2018/1/20
// * @Modified By:
// */
//@ConfigurationProperties(prefix = "redis.cache")
//@Component("jedisConfig")
//@RefreshScope
//public class JedisConfig {
//    // 代表连接地址
//    private String host;
//
//    // 代表连接port
//    private int port;
//
//    public String getHost() {
//        return host;
//    }
//
//    public void setHost(String host) {
//        this.host = host;
//    }
//
//    public int getPort() {
//        return port;
//    }
//
//    public void setPort(int port) {
//        this.port = port;
//    }
//}
