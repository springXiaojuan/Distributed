package com.xxj.redis.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 2:13 下午
 */
@Service
public class JedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void testStr() {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set("ttK","22");
        System.out.println(valueOperations.get("ttK"));
    }

    public void testStr2() {
        BoundValueOperations ttk2Operation = redisTemplate.boundValueOps("ttk2");
        ttk2Operation.set("sss");
        System.out.println(ttk2Operation.get());
    }

    public void testStr3() {
        BoundValueOperations ttk2Operation = redisTemplate.boundValueOps("ttk2");
        ttk2Operation.set("sss");
        ttk2Operation.setIfAbsent("ss");

        ttk2Operation.setIfPresent("wwww");

        System.out.println(ttk2Operation.get());



    }

    /**
     * @Nullable
     *     public Boolean setIfPresent(K key, V value) {
     *         byte[] rawKey = this.rawKey(key);
     *         byte[] rawValue = this.rawValue(value);
     *         return (Boolean)this.execute((connection) -> {
     *             return connection.set(rawKey, rawValue, Expiration.persistent(), RedisStringCommands.SetOption.ifPresent());
     *         }, true);
     *     }
     */

    /**
     * public Boolean setIfAbsent(K key, V value) {
     *         byte[] rawKey = this.rawKey(key);
     *         byte[] rawValue = this.rawValue(value);
     *         return (Boolean)this.execute((connection) -> {
     *             return connection.setNX(rawKey, rawValue);
     *         }, true);
     *     }
     */


}
