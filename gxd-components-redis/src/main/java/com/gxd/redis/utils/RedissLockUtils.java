package com.gxd.redis.utils;

import com.gxd.redis.lock.DistributedLocker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author:gxd
 * @Description:redis分布式锁帮助类
 * @Date: 11:55 2018/1/9
 * @Modified By:
 */
public class RedissLockUtils {
    private static final Logger logger = LoggerFactory.getLogger(RedissLockUtils.class);

    private static DistributedLocker redissLock;

    public static void setLocker(DistributedLocker locker) {
        redissLock = locker;
    }

    public static void lock(String lockKey) {
        redissLock.lock(lockKey);
        logger.debug("======lock======"+Thread.currentThread().getName());
    }

    public static void unlock(String lockKey) {
        redissLock.unlock(lockKey);
        logger.debug("======unlock======"+Thread.currentThread().getName());    }

    /**
     * 带超时的锁
     * @param lockKey
     * @param timeout 超时时间   单位：秒
     */
    public static void lock(String lockKey, int timeout) {
        redissLock.lock(lockKey, timeout);
        logger.debug("======lock======"+Thread.currentThread().getName());
    }

    /**
     * 带超时的锁
     * @param lockKey
     * @param unit 时间单位
     * @param timeout 超时时间
     */
    public static void lock(String lockKey, TimeUnit unit , int timeout) {
        redissLock.lock(lockKey, unit, timeout);
        logger.debug("======lock======"+Thread.currentThread().getName());
    }
}
