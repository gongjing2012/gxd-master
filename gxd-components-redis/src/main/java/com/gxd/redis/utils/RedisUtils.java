package com.gxd.redis.utils;

import com.google.common.base.Strings;
import com.gxd.redis.config.ClusterConfigurationProperties;
import com.gxd.common.utils.FastJsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author:gxd
 * @Description:
 * @Date: 11:53 2018/1/5
 * @Modified By:
 */
@Component
//@EnableApolloConfig
public class RedisUtils {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @SuppressWarnings("SpringJavaAutowiringInspection") //加这个注解让IDE 不报: Could not autowire
    @Autowired
    private RedisTemplate redisTemplate;

    @SuppressWarnings("SpringJavaAutowiringInspection") //加这个注解让IDE 不报: Could not autowire
    @Autowired
    private ClusterConfigurationProperties clusterConfigurationProperties;


    //@ApolloConfigChangeListener
    //public void onChange(ConfigChangeEvent changeEvent) {
    //    //logger.info("before refresh {}", redisClusterProperties.getNodes().toString()+",MaxAttempts:"+redisClusterProperties.getMaxAttempts());
    //    //ContextRefresher refresher = new ContextRefresher((ConfigurableApplicationContext) SpringUtils.getBean("clusterConfigurationProperties"), refreshScope);
    //    //refresher.refresh();
    //    //ConfigurableApplicationContext context = SpringApplication.run(ClusterConfigurationProperties.class,
    //    //        "--spring.main.webEnvironment=false", "--debug=false",
    //    //        "--spring.main.bannerMode=OFF");
    //    //context.getEnvironment().setActiveProfiles("refresh");
    //    //ContextRefresher refresher = new ContextRefresher(context, refreshScope);
    //    //refresher.refresh();
    //    logger.info("before refresh: " + clusterConfigurationProperties.getNodes() + " , " + clusterConfigurationProperties.getMaxRedirects());
    //    refreshScope.refresh("clusterConfigurationProperties");
    //    ConfigurableApplicationContext context = SpringApplication.run(CacheConfig.class,
    //            "--spring.main.webEnvironment=false", "--debug=false",
    //            "--spring.main.bannerMode=OFF");
    //    context.getEnvironment().setActiveProfiles("refresh");
    //    ContextRefresher refresher = new ContextRefresher(context, refreshScope);
    //    refresher.refresh();
    //    logger.info("after refresh: " + clusterConfigurationProperties.getNodes() + " , " + clusterConfigurationProperties.getMaxRedirects());
    //}


    /**
     * 普通缓存放入
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, final String value) {
        redisTemplate.opsForValue().set(key, value);
        return true;
    }

    /**
     * 普通缓存get
     * @param key
     * @return
     */
    public String get(final String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */

    public String set(String key, String value, int time){
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return "ok";
        } catch (Exception e) {
            logger.error("异常");
            return "false";
        }
    }


    public String setObject(String key, Object value, int time){
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                return "false";
            }
            return "ok";
        } catch (Exception e) {
            return "false";
        }
    }

    public Object getObject(final String key){
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * 指定缓存失效时间
     * @param key 键
     * @param value 值
     * @param expire 时间(秒)
     * @return
     */

    public void expire(final String key,final Object value, long expire) {
         redisTemplate.opsForValue().set(key, value,expire,TimeUnit.SECONDS);
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */

    public int getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS).intValue();
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */

    public boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */

    public void del(String ... key){
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    /**
     * 递增
     * @param key 键
     * @param delta 要增加几(大于0)
     * @return
     */

    public long incr(String key, long delta){
        if(delta < 0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key 键
     * @param delta 要减少几(小于0)
     * @return
     */

    public long decr(String key, long delta){
        if(delta < 0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }
    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */

    public Object hget(String key, String item){
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */

    public Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */

    public boolean hmset(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */

    public boolean hmset(String key, Map<String,Object> map, int time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time > 0){
                expire(key, map,time);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */

    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */

    public void hdel(String key, Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */

    public boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return
     */

    public double hincr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    public long hincr(String key, String item, long by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少记(小于0)
     * @return
     */

    public double hdecr(String key, String item, double by){
        return redisTemplate.opsForHash().increment(key, item,-by);
    }

    public long hdecr(String key, String item, long by){
        return redisTemplate.opsForHash().increment(key, item,-by);
    }

    //============================set=============================
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */

    public Set<Object> sGet(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            return null;
        }
    }
    /**
     * 根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */

    public boolean sHasKey(String key, Object value){
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */

    public long sSet(String key, Object...values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */

    public long sSetAndTime(String key, long time, Object...values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if(time > 0) {
                expire(key,values, time);
            }
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */

    public long sGetSetSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * set重命名
     * @param oldkey
     * @param newkey
     */

    public void srename(String oldkey, String newkey){
        redisTemplate.boundSetOps(oldkey).rename(newkey);
    }
    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */

    public long setRemove(String key, Object ...values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 添加set
     * @param key
     * @param value
     */

    public void sadd(String key, String... value) {
            redisTemplate.boundSetOps(key).add(value);
    }

    /**
     * 删除set集合中的对象
     * @param key
     * @param value
     */

    public  void srem(String key, String... value) {
        redisTemplate.boundSetOps(key).remove(value);
    }

//===============================list=================================

    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束  0 到 -1代表所有值
     * @return
     */

    public List<Object> lGet(String key, long start, long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */

    public long lGetListSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            return 0;
        }
    }


    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */

    public boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */

    public boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key,value, time);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @return
     */

    public boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */

    public boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, value,time);
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */

    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */

    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            return 0;
        }
    }
    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */

    public Object lGetIndex(String key, long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合获取
     * @param key
     * @param from
     * @param to
     * @return
     */
    public Set<Object> rangeByScore(String key,double from,double to){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, from, to);
    }

    public <T> boolean setList(String key, List<T> list) {
        String value = FastJsonUtils.toJSONString(list);
        return set(key,value);
    }


    public <T> List<T> getList(String key,Class<T> clz) {
        String json = get(key);
        if(json!=null){
            List<T> list = FastJsonUtils.toList(json, clz);
            return list;
        }
        return null;
    }

    public Long generatorId(String biz) {
        // 转成数字类型，可排序
        return incrOrderId(biz, getOrderPrefix(new Date()));
    }
    /**
     * @Description 支持一个小时100w个订单号的生成
     *
     * @author biz
     * @param prefix
     * @return
     */
    private Long incrOrderId(String biz, String prefix) {
        String orderId = null;
        String key = "geese:#{biz}:id:".replace("#{biz}", biz).concat(prefix); // 00001
        try {
            Long index = redisTemplate.opsForValue().increment(key,1L);
            orderId = prefix.concat(String.format("%1$05d", index)); // 补位操作 保证满足5位
        } catch(Exception ex) {
            System.out.println("分布式订单号生成失败异常。。。。。");
        }
        if (Strings.isNullOrEmpty(orderId)) {
            return null;
        }
        return Long.parseLong(orderId);
    }
    /**
     * @Description
     *
     * @author butterfly
     * @param date
     * @return
     */
    private String getOrderPrefix(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int day = c.get(Calendar.DAY_OF_YEAR); // 今天是第多少天
        int hour =  c.get(Calendar.HOUR_OF_DAY);
        String dayFmt = String.format("%1$03d", day); // 0补位操作 必须满足三位
        String hourFmt = String.format("%1$02d", hour);  // 0补位操作 必须满足2位
        StringBuffer prefix = new StringBuffer();
        prefix.append((year - 2000)).append(dayFmt).append(hourFmt);
        return prefix.toString();
    }
}
