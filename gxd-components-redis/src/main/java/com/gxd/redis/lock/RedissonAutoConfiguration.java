package com.gxd.redis.lock;

import com.gxd.redis.utils.RedissLockUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author:gxd
 * @Description:
 * @Date: 11:54 2018/1/9
 * @Modified By:
 */
@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {
    @Autowired
    private RedissonProperties redissionProperties;

    /**
     * 哨兵模式自动装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="spring.redisson.master-name")
    public RedissonClient redissonSentinel() {
        Config config = new Config();
        SentinelServersConfig serverConfig = config.useSentinelServers().addSentinelAddress(redissionProperties.getSentinelAddresses())
                .setMasterName(redissionProperties.getMasterName())
                .setTimeout(redissionProperties.getTimeout())
                .setMasterConnectionPoolSize(redissionProperties.getMasterConnectionPoolSize())
                .setSlaveConnectionPoolSize(redissionProperties.getSlaveConnectionPoolSize());

        if(StringUtils.isNotBlank(redissionProperties.getPassword())) {
            serverConfig.setPassword(redissionProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 单机模式自动装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="spring.redisson.address")
    public RedissonClient redissonSingle() {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer()
                .setAddress(redissionProperties.getAddress())
                .setTimeout(redissionProperties.getTimeout())
                .setConnectionPoolSize(redissionProperties.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redissionProperties.getConnectionMinimumIdleSize());

        if(StringUtils.isNotBlank(redissionProperties.getPassword())) {
            serverConfig.setPassword(redissionProperties.getPassword());
        }
        return Redisson.create(config);
    }

    /**
     * 集群模式自动装配
     * @return
     */
    @Bean
    @ConditionalOnProperty(name="spring.redisson.nodeAddresses")
    public RedissonClient redissonCluster() {
        Config config = new Config();
        List<String> nodeAddresses = redissionProperties.getNodeAddresses();
        ClusterServersConfig serverConfig = config.useClusterServers()
                .addNodeAddress(nodeAddresses.toArray(new String[] {}))
                .setTimeout(redissionProperties.getTimeout())
                .setScanInterval(redissionProperties.getScanInterval()) //设置集群状态扫描时间
                .setMasterConnectionPoolSize(redissionProperties.getMasterConnectionPoolSize()) //设置连接数
                .setSlaveConnectionPoolSize(redissionProperties.getSlaveConnectionPoolSize());

        if(StringUtils.isNotBlank(redissionProperties.getPassword())) {
            serverConfig.setPassword(redissionProperties.getPassword());
        }

        return Redisson.create(config);
    }

    /**
     * 装配locker类，并将实例注入到RedissLockUtil中
     * @return
     */
    @Bean
    DistributedLocker distributedLocker(RedissonClient redissonClient) {
        DistributedLocker locker = new RedissonDistributedLocker();
        locker.setRedissonClient(redissonClient);
        RedissLockUtils.setLocker(locker);
        return locker;
    }
}
