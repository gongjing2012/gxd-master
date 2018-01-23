//package com.gxd.redis.web;
//
//import com.gxd.redis.lock.DistributedRedisLock;
//import com.gxd.redis.lock.RedissonManager;
//import com.gxd.redis.utils.RedissLockUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//
///**
// * @Author:gxd
// * @Description:
// * @Date: 9:57 2018/1/9
// * @Modified By:
// */
//@RestController
//public class RedderController {
//    @RequestMapping("/")
//    public String redder() throws IOException {
//        RedissonManager.init("","");
//        String key = "test123";
//
//        DistributedRedisLock.acquire(key);
//
//
//        Long result =  RedissonManager.nextID();
//
//        DistributedRedisLock.release(key);
//
//        RedderController.redisLock();
//        return ""+result;
//    }
//
//    private static void redisLock(){
//        RedissonManager.init(); //初始化
//        for (int i = 0; i < 100; i++) {
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        String key = "test1234";
//                        //DistributedRedisLock.acquire(key);
//                        RedissLockUtils.lock(key);
//
//                        Thread.sleep(1000); //获得锁之后可以进行相应的处理
//                        System.err.println("======获得锁后进行相应的操作======");
//                        //DistributedRedisLock.release(key);
//                        RedissLockUtils.unlock(key);
//                        System.err.println("=============================");
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//            t.start();
//        }
//    }
//
//    public static void main(String[] args) {
//        RedderController.redisLock();
//    }
//}
