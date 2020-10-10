package com.xxj.dubbo.service;

import com.xxj.dubbo.entity.User;

/**
 * @author  xuxiaojuan
 * @date  2020/9/24 10:44 上午
 */

public interface IUserService {

    User getUser(Long userId);

    String sayHello();
}
