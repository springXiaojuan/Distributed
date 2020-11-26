package com.xxj.redis.spring.service;

import com.xxj.redis.config.RedisConfig;
import io.lettuce.core.RedisFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 2:48 下午
 */

@Service
public class LettuceService {

    @Autowired
    private RedisConfig redisConfig;

    public void testStr() {
//        RedisFuture sadd = redisConfig.LettuceTemplate().sadd("ss", "sss");
//        System.out.println(sadd.get());
        ValueOperations<String, Object> valueOperations = redisConfig.redisTemplate().opsForValue();
        valueOperations.set("LLK1","VV2");
        System.out.println(valueOperations.get("LLK1"));
    }
}
