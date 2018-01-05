//package com.gxd.redis.config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Author:gxd
// * @Description:
// * @Date: 15:06 2018/1/5
// * @Modified By:
// */
//@Configuration
//@ConfigurationProperties(prefix = "spring.redis.cluster")
//public class RedisClusterProperties {
//
//    //集群节点
//    private List<String> nodes = new ArrayList<>();
//
//    public List<String> getNodes() {
//        return nodes;
//    }
//
//    public void setNodes(List<String> nodes) {
//        this.nodes = nodes;
//    }
//}