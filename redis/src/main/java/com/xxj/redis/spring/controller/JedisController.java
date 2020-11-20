package com.xxj.redis.spring.controller;

import com.xxj.redis.spring.service.JedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 2:12 下午
 */
@RestController
public class JedisController {

    @Autowired
    private JedisService jedisService;

    @PostMapping("/jedis/str")
    public void jedisStrTest() {
        jedisService.testStr();
    }

    @PostMapping("/jedis/str2")
    public void jedisStrTest2() {
        jedisService.testStr2();
    }

}
