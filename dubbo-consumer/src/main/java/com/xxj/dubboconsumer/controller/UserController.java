package com.xxj.dubboconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xxj.dubbo.entity.User;
import com.xxj.dubbo.service.IUserService;
import lombok.extern.slf4j.Slf4j;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author  xuxiaojuan
 * @date  2020/9/24 11:42 上午
 */
@RestController
@Slf4j
public class UserController {

    @DubboReference(url = "dubbo://127.0.0.1:12345")
    private IUserService userService;

    @PostMapping("/testUser")
    public  void testUser() {
        String s = userService.sayHello();
        log.info(s);
        User user = userService.getUser(1L);
        log.info("user === {}",user);
    }
}
