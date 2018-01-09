package com.gxd.redis.lock;

import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @Author:gxd
 * @Description:Lock的接口定义类
 * @Date: 11:25 2018/1/9
 * @Modified By:
 */
public interface  DistributedLocker {
    void lock(String lockKey);

    void unlock(String lockKey);

    void lock(String lockKey, int timeout);

    void lock(String lockKey, TimeUnit unit , int timeout);

    void setRedissonClient(RedissonClient redissonClient);
}
