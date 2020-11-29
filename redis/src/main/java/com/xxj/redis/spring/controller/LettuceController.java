package com.xxj.redis.spring.controller;

import com.xxj.redis.spring.service.LettuceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 3:03 下午
 */
@RestController
public class LettuceController {

    @Autowired
    private LettuceService lettuceService;

    @PostMapping("/lettuce/str")
    public void jedisStrTest() {
        lettuceService.testStr();
    }

    @PostMapping("/lettuce/test")
    public void jedisTest() {
        lettuceService.test();
    }
}
