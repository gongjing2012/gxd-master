package com.gxd.redis.web;

import com.gxd.redis.lock.DistributedRedisLock;
import com.gxd.redis.lock.RedissonManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author:gxd
 * @Description:
 * @Date: 9:57 2018/1/9
 * @Modified By:
 */
@RestController
public class RedderController {
    @RequestMapping("/")
    public String redder() throws IOException {
        RedissonManager.init("","");
        String key = "test123";

        DistributedRedisLock.acquire(key);


        Long result =  RedissonManager.nextID();

        DistributedRedisLock.release(key);
        return ""+result;
    }
}
