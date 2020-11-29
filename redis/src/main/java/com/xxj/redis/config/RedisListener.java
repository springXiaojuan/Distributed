package com.xxj.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * 实现对键通知的监听
 * @author xuxiaojuan
 * @date 2020/11/26 11:34 上午
 */
public class RedisListener implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] pattern) {
//        System.out.println(new String(message.getBody()) + ":" +
//                        new String(message.getChannel()) + ":" +
//                        new String(pattern) );
    }
}
