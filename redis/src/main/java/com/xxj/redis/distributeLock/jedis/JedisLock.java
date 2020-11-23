package com.xxj.redis.distributeLock.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 3:40 下午
 */
@Component
public class JedisLock {

    @Autowired
    private RedisTemplate redisTemplate;

    private static String REDIS_PREFIX = "redis:lock:";

    private static final Long lockTimeOut = 30000L;

    static {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        RedisTemplate<Object, Object> objectObjectRedisTemplate = new RedisTemplate<>();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Set keys = objectObjectRedisTemplate.keys(REDIS_PREFIX + "*");
            keys.forEach(key -> {
                Long leftExpire = objectObjectRedisTemplate.getExpire(key);
                System.out.println("leftExpire ========= " + leftExpire);
                if(leftExpire >= -1 && leftExpire < (lockTimeOut - 10000)/1000 ) {
                    objectObjectRedisTemplate.boundValueOps(key).expire(Duration.ofMillis(lockTimeOut));
                }
            });
        },5,3, TimeUnit.SECONDS);
    }

    public String lock(String lockName,Long acquireTimeout, Long timeout) {
        Long endTime = System.currentTimeMillis() + acquireTimeout;
        String documentId = UUID.randomUUID().toString();
        try {
            while(System.currentTimeMillis() < endTime) {
                   if(redisTemplate.opsForValue().setIfAbsent(REDIS_PREFIX + lockName, documentId)) {
                    redisTemplate.boundValueOps(REDIS_PREFIX + lockName).expire(Duration.ofMillis(timeout));
                    return documentId;
                }
                // 如果key 存在但是没有设置过期时间，需要重新加上过期时间
                if(-1 ==  redisTemplate.getExpire(REDIS_PREFIX + lockName) ) {
                    redisTemplate.boundValueOps(REDIS_PREFIX + lockName).expire(Duration.ofMillis(timeout));
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


    public void releaseRedisLock(String lockName,String uniqueValue) {
        String redisKey = REDIS_PREFIX + lockName;
        try {
            if(redisTemplate.opsForValue().get(redisKey).equals(uniqueValue)) {
                redisTemplate.delete(redisKey);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
