package com.xxj.redis.lettuce;


import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

/**
 * @author xuxiaojuan
 * @date 2020/11/19 5:57 下午
 */
public class LettuceStrTest {

    public static void main(String[] args) {
        RedisClient redisClient = RedisClient.create("redis://123456@10.1.1.240:6379");
        StatefulRedisConnection<String, String> connect = redisClient.connect();
        RedisAsyncCommands<String, String> sysCommand = connect.async();
        sysCommand.flushdb();



    }

}
