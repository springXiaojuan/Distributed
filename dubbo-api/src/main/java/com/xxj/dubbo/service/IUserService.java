package com.xxj.dubbo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxj.dubbo.entity.User;
import com.xxj.dubbo.mapper.UserMapper;

/**
 * @author  xuxiaojuan
 * @date  2020/9/24 10:44 上午
 */

public interface IUserService {

    User getUser(Long userId);

    String sayHello();

    void addUserBatch();
}
