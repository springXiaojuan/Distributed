package com.xxj.redis.lua;

import io.lettuce.core.internal.LettuceLists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuxiaojuan
 * @date 2020/11/29 9:18 下午
 */
@Component
public class luaTest {

    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate (RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

   public void testLuaSet() {
       List<String> keyList = new ArrayList<>();
       keyList.add("0");
//       "redis.call('set', 'k1', 'v1')"
       Object execute = redisTemplate.execute(RedisScript.of("redis.call('set','k1','v1')"), keyList );
       System.out.println(execute);
   }

    public void testLuaGet() {
        List<String> keyList = new ArrayList<>();
        keyList.add("0");
        Object execute = redisTemplate.execute(RedisScript.of("return redis.call('get','k1')",String.class),keyList );
        System.out.println(execute);
    }

    public void testLuaGet2() {
        List<String> keyList = new ArrayList<>();
        keyList.add("0");
        Object execute1 = redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] eval = connection.eval("return redis.call('get','k1')".getBytes(), ReturnType.VALUE, 0);
                return new String(eval);
            }

        });

        System.out.println(execute1);
    }

}
