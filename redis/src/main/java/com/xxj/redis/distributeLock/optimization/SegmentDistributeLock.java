package com.xxj.redis.distributeLock.optimization;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.xxj.redis.config.RedisConfig;
import com.xxj.redis.utils.ConvertArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 3:40 下午
 */
@Component
public class SegmentDistributeLock {

    private static RedisTemplate redisTemplate;


    private static SegmentLock segmentLock;

    private static String REDIS_PREFIX = "redis:lock:";

    private static final Long lockTimeOut = 30000L;

    private static Boolean IS_SEGMENT_LOCK = false;

    public static final String REDISLOCK_KEY = "redisLockKey";

    public static final String REDISLOCK_VALUE = "redisLockValue";

    public static final String STORE_KEY = "storeKey";

    @Autowired
    public void setSegmentLock(SegmentLock segmentLock) {
        SegmentDistributeLock.segmentLock = segmentLock;
    }


    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        SegmentDistributeLock.redisTemplate = redisTemplate;
    }



//    static {
//        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        RedisTemplate<Object, Object> objectObjectRedisTemplate = new RedisTemplate<>();
//
//        scheduledExecutorService.scheduleAtFixedRate(() -> {
//            Set keys = objectObjectRedisTemplate.keys(REDIS_PREFIX + "*");
//            keys.forEach(key -> {
//                Long leftExpire = objectObjectRedisTemplate.getExpire(key);
//                System.out.println("leftExpire ========= " + leftExpire);
//                if(leftExpire >= -1 && leftExpire < (lockTimeOut - 10000)/1000 ) {
//                    objectObjectRedisTemplate.boundValueOps(key).expire(Duration.ofMillis(lockTimeOut));
//                }
//            });
//        },5,3, TimeUnit.SECONDS);
//    }



    public Map<String, String> lock(String lockName, Long acquireTimeout, Long timeout, List<String> lockSegmentLock) {
        // 分布式锁的map 对象
        Map<String, String> map = new ConcurrentHashMap<>();
        String redisLockKey = null;

        Long endTime = System.currentTimeMillis() + acquireTimeout;
        String documentId = UUID.randomUUID().toString();
        try {
            while(System.currentTimeMillis() < endTime) {

                // 判断是否需要使用分布式锁
                String storeKey = null;
                if(IS_SEGMENT_LOCK) {
                    // redis:store:0
                    storeKey = segmentLock.randomSegment(lockSegmentLock);
                    // redis:lock:updateGoodStoreredis:store:0
                    redisLockKey = REDIS_PREFIX  + lockName + storeKey;
                }
                if(redisTemplate.opsForValue().setIfAbsent(redisLockKey, documentId)) {
                    redisTemplate.boundValueOps(redisLockKey).expire(Duration.ofMillis(timeout));
                    map.put(SegmentDistributeLock.REDISLOCK_KEY,redisLockKey);
                    map.put(SegmentDistributeLock.REDISLOCK_VALUE,documentId);
                    map.put(SegmentDistributeLock.STORE_KEY,storeKey);
                    return map;
                }
                // 如果key 存在但是没有设置过期时间，需要重新加上过期时间
                if(-1 ==  redisTemplate.getExpire(redisLockKey) ) {
                    redisTemplate.boundValueOps(redisLockKey).expire(Duration.ofMillis(timeout));
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }


    public void releaseRedisLock(Map<String,String> lockMap) {

        String redisLockKey = lockMap.get(SegmentDistributeLock.REDISLOCK_KEY);
        String redisLockValue = lockMap.get(SegmentDistributeLock.REDISLOCK_VALUE);
        try {
            if(redisTemplate.opsForValue().get(redisLockKey).equals(redisLockValue)) {
                redisTemplate.delete(redisLockKey);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SegmentDistributeLock() {

    }

    public SegmentDistributeLock(boolean isSegmentLock ) {
        IS_SEGMENT_LOCK  = isSegmentLock;
    }

    public boolean isAllSegmentOver(List<String> lockSegmentList) {

        Set keys = redisTemplate.keys(ConvertArray.STORE_PREFIX + "*");

        return keys.size() == lockSegmentList.size() ? true : false;
    }



}
