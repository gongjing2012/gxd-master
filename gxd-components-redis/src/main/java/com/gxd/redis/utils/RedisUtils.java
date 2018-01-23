//package com.gxd.redis.utils;
//
//import com.ctrip.framework.apollo.model.ConfigChangeEvent;
//import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
//import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
//import com.gxd.redis.config.JedisClusterConfig;
//import com.gxd.redis.config.RedisClusterProperties;
//import com.gxd.utils.FastJsonUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.context.scope.refresh.RefreshScope;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.JedisCluster;
//
//import java.io.IOException;
//import java.util.*;
//
///**
// * @Author:gxd
// * @Description:
// * @Date: 11:53 2018/1/5
// * @Modified By:
// */
//@Component
//@EnableApolloConfig
//public class RedisUtils implements InitializingBean, DisposableBean {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    //@SuppressWarnings("SpringJavaAutowiringInspection") //加这个注解让IDE 不报: Could not autowire
//    private JedisCluster jedisCluster;
//
//    @SuppressWarnings("SpringJavaAutowiringInspection") //加这个注解让IDE 不报: Could not autowire
//    @Autowired
//    private JedisClusterConfig jedisClusterConfig;
//
//
//    //@Autowired
//    //private JedisConfig jedisConfig;
//    // jedis 实例
//    //private Jedis jedis = null;
//
//
//    //    @Autowired
////    private RedisTemplate redisTemplate;
////    @Autowired
////    private ClusterConfigurationProperties clusterProperties;
////
//    @SuppressWarnings("SpringJavaAutowiringInspection") //加这个注解让IDE 不报: Could not autowire
//    @Autowired
//    private RefreshScope refreshScope;
//    @Autowired
//    private RedisClusterProperties redisClusterProperties;
//
//    @ApolloConfigChangeListener
//    public void onChange(ConfigChangeEvent changeEvent) {
//        logger.info("before refresh {}", redisClusterProperties.getNodes().toString()+",MaxAttempts:"+redisClusterProperties.getMaxAttempts());
//        refreshScope.refresh("redisClusterProperties");
//        logger.info("after refresh {}", redisClusterProperties.getNodes().toString()+",MaxAttempts:"+redisClusterProperties.getMaxAttempts());
//        changeJedisCluster();
//    }
//
//
//    public Object getObject(String key) throws IOException {
//        String string = jedisCluster.get(key);
//        Object json2Object = FastJsonUtils.toBean(string, Object.class);
//        return json2Object;
//    }
//
//    public <T> String addByKey(String key, T object) throws IOException {
//        String object2JsonString = FastJsonUtils.toJSONString(object);
//        String set = jedisCluster.set(key, object2JsonString);
//        return set;
//    }
//    public <T> String add(T object) throws IOException {
//        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
//        String object2JsonString = FastJsonUtils.toJSONString(object);
//        jedisCluster.set(uuid, object2JsonString);
//        return uuid;
//    }
//
//
//
//    public <T> List<String> addList(List<T> list) throws IOException {
//        List<String> sum = new ArrayList<>(70);
//        String uuid = null;
//        String str = null;
//        for (int i = 0; i < list.size(); i++) {
//            uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
//            str = FastJsonUtils.toJSONString(list.get(i));
//            jedisCluster.set(uuid, str);
//            sum.set(i, uuid);
//        }
//        return sum;
//    }
//
//    public <T> String addListKey(List<String> strList, List<T> list) throws IOException {
//        return null;
//    }
//
//    public <T> Long addListKey(Map<String, T> map) throws IOException {
//        Long sum = (long) 0;
//        String str = null;
//        Iterator<Map.Entry<String, T>> iterator = map.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, T> entry = (Map.Entry<String, T>) iterator.next();
//            String key = entry.getKey();
//            T object = entry.getValue();
//            str = FastJsonUtils.toJSONString(object);
//            jedisCluster.set(key, str);
//            sum = sum + 1;
//        }
//        return sum;
//    }
//
//    public Long deleteByKey(String key) throws IOException {
//        Long del = jedisCluster.del(key);
//        return del;
//    }
//
//    public Long batchDelete(List<String> strList) throws IOException {
//        Long sum = (long) 0;
//        Long del = (long) 0;
//        for (int i = 0; i < strList.size(); i++) {
//            del = jedisCluster.del(strList.get(i));
//            sum = sum + del;
//        }
//        return sum;
//    }
//
//
//    @Override
//    public void destroy() throws Exception {
//        if (jedisCluster != null) {
//            jedisCluster.close();
//        }
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        jedisCluster = jedisClusterConfig.createJedisCluster();
//    }
//
//    /**
//     * 更改Jedis
//     */
//    public void changeJedisCluster() {
//
//        logger.info("after refresh {}", redisClusterProperties.getNodes().toString()+",MaxAttempts:"+redisClusterProperties.getMaxAttempts());
//
//        jedisCluster = jedisClusterConfig.createJedisCluster();
//
//        logger.info("change ok.");
//    }
//
//
//
//
//
//    ////////////////////////////////////////////////////////////
//    //@Override
//    //public void destroy() throws Exception {
//    //    if (jedis != null) {
//    //        jedis.disconnect();
//    //    }
//    //}
//    //
//    //@Override
//    //public void afterPropertiesSet() throws Exception {
//    //    jedis = JedisUtils.createJedis(jedisConfig.getHost(), jedisConfig.getPort(),"123456");
//    //}
//    //
//    ///**
//    // * 更改Jedis
//    // */
//    //public void changeJedis() {
//    //
//    //    logger.info("start to change jedis hosts to: " + jedisConfig.getHost() + " : " + jedisConfig.getPort());
//    //
//    //
//    //    jedis = JedisUtils.createJedis(jedisConfig.getHost(), jedisConfig.getPort(),"123456");
//    //
//    //
//    //    logger.info("change ok.");
//    //}
//}
