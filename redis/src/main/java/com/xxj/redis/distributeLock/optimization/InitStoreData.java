package com.xxj.redis.distributeLock.optimization;

import com.xxj.redis.utils.ConvertArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author xuxiaojuan
 * @date 2020/11/26 5:53 下午
 */
public class InitStoreData {


    public static void main(String[] args) {

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        RedisTemplate<Object, Object> objectObjectRedisTemplate = new RedisTemplate<>();

        scheduledExecutorService.schedule(() -> {
            for (int i = 0; i < 20; i++) {
                objectObjectRedisTemplate.boundValueOps(ConvertArray.STORE_PREFIX + i).set(String.valueOf(50));
            }
        },3, TimeUnit.SECONDS);

        scheduledExecutorService.shutdown();

        try {
            if(!scheduledExecutorService.awaitTermination(30,TimeUnit.SECONDS)){
                scheduledExecutorService.shutdown();
            }
        }catch (Exception e) {
            e.printStackTrace();
            scheduledExecutorService.shutdown();
        }

    }



}
