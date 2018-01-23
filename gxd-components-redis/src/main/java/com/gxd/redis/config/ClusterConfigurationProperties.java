package com.gxd.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:gxd
 * @Description:
 * @Date: 13:23 2018/1/19
 * @Modified By:
 */
@ConfigurationProperties(prefix = "spring.redis.cluster")
@Component("clusterConfigurationProperties")
@RefreshScope
public class ClusterConfigurationProperties {
    private List<String> nodes;

    private int maxRedirects;

    /**
     * Get initial collection of known cluster nodes in format {@code host:port}.
     *
     * @return
     */
    public List<String> getNodes() {
        return nodes;
    }

    public void setNodes(List<String> nodes) {
        this.nodes = nodes;
    }

    public int getMaxRedirects() {
        return maxRedirects;
    }

    public void setMaxRedirects(int maxRedirects) {
        this.maxRedirects = maxRedirects;
    }
}
