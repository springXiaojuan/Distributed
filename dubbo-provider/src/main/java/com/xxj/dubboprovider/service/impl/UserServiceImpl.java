package com.xxj.dubboprovider.service.impl;

import com.xxj.dubbo.entity.User;
import com.xxj.dubbo.service.IUserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * @author  xuxiaojuan
 * @date  2020/9/24 10:51 上午
 */
@DubboService
public class UserServiceImpl implements IUserService {


    @Value("${dubbo.application.name}")
    private String serviceName;

    public User getUser(Long userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }


    public String sayHello() {
        return  serviceName + "： hello Dubbo";
    }
}
