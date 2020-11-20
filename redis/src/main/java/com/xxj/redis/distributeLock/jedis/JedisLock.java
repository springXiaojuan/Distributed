package com.xxj.redis.distributeLock.jedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.UUID;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 3:40 下午
 */
@Component
public class JedisLock {


    @Autowired
    private RedisTemplate redisTemplate;

    private static String REDIS_PREFIX = "redis:lock:";

    public String lock(String lockName,Long acquireTimeout, Long timeout) {
        Long endTime = System.currentTimeMillis() + acquireTimeout;
        String documentId = UUID.randomUUID().toString();
        try {
            while(System.currentTimeMillis() < endTime) {
                if(redisTemplate.opsForValue().setIfAbsent(REDIS_PREFIX + lockName, documentId)) {
                    redisTemplate.boundValueOps(REDIS_PREFIX + lockName).expire(Duration.ofSeconds(timeout));
                    return documentId;
                }
                // 如果key 存在但是没有设置过期时间，需要重新加上过期时间
                if(-1 ==  redisTemplate.getExpire(REDIS_PREFIX + lockName) ) {
                    redisTemplate.boundValueOps(REDIS_PREFIX + lockName).expire(Duration.ofSeconds(timeout));
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
