package com.gxd.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:gxd
 * @Description:
 * @Date: 15:06 2018/1/5
 * @Modified By:
 */
@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterProperties {

    //集群节点
    private List<String> nodes = new ArrayList<>();
    /** 连接超时时间 */
    private int timeout;
    /** 重连次数 */
    private int maxAttempts;
    /**集群标识 */
    private boolean clusterFlag;

    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public int getTimeout() {
        return timeout;
    }
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    public int getMaxAttempts() {
        return maxAttempts;
    }
    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public boolean getClusterFlag() {
        return clusterFlag;
    }

    public void setClusterFlag(boolean clusterFlag) {
        this.clusterFlag = clusterFlag;
    }
}