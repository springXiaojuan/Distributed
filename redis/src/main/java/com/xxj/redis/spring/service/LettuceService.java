package com.xxj.redis.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 2:48 下午
 */

@Service
public class LettuceService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void testStr() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("LLK1","VV1");
        System.out.println(valueOperations.get("LLK1"));
    }
}
