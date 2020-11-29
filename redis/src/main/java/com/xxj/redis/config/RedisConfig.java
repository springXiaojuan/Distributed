package com.xxj.redis.config;

import io.lettuce.core.ConnectionFuture;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 12:00 下午
 */
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;


    /**
     * jedis
     * @return
     */
//    @Bean
//    public JedisConnectionFactory jedisConnectionFactory() {
//        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName(host);
//        redisStandaloneConfiguration.setPort(port);
//        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfigurationBuilder = JedisClientConfiguration.builder();
//        jedisClientConfigurationBuilder.usePooling();
//        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfigurationBuilder.build() );
//    }
//
//    @Bean
//    public JedisPool jedisPool() {
//        return new JedisPool(host,port);
//    }


    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder builder = LettucePoolingClientConfiguration.builder();
        return new LettuceConnectionFactory(redisStandaloneConfiguration,builder.build());
    }


    @Bean
    public RedisTemplate<String,Object> redisTemplate() {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory());
        redisTemplate.setDefaultSerializer(RedisSerializer.string());
        return redisTemplate;
    }

//    @Bean
//    public RedisAsyncCommands LettuceTemplate() throws ExecutionException, InterruptedException {
//        RedisClient redisClient = RedisClient.create();
//        ConnectionFuture<StatefulRedisConnection<String, String>> statefulRedisConnectionConnectionFuture = redisClient.connectAsync(StringCodec.UTF8, RedisURI.create(host, port));
//        RedisAsyncCommands<String, String> async = statefulRedisConnectionConnectionFuture.get().async();
//        return async;
//    }


//    @Bean
//    public void LettuceTemplate() {
//        RedisClient redisClient = RedisClient.create();
//    }

    @Bean
    public RedisListener redisListener() {
        return new RedisListener();
    }

    @Bean
    public RedisMessageListenerContainer container() {

        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(lettuceConnectionFactory());
        List<Topic>  topicList = new ArrayList<>();
        topicList.add(new PatternTopic("__keyspace@*__:*"));
        topicList.add(new PatternTopic("__keyevent@*__:*"));
        redisMessageListenerContainer.addMessageListener(redisListener(),topicList);
        return redisMessageListenerContainer;
    }

}
