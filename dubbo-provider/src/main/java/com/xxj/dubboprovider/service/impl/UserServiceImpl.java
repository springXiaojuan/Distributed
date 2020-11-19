package com.xxj.dubboprovider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxj.dubbo.entity.User;
import com.xxj.dubbo.mapper.UserMapper;
import com.xxj.dubbo.service.IUserService;
import lombok.Generated;
import lombok.extern.log4j.Log4j2;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author  xuxiaojuan
 * @date  2020/9/24 10:51 上午
 */
@DubboService
@Log4j2
public class UserServiceImpl  implements IUserService {


    @Value("${dubbo.application.name}")
    private String serviceName;

    @Autowired
    private UserMapper userMapper;


    @Override
    public User getUser(Long userId) {
        log.info("userId ============ {}" ,userId);
//        User user = new User();
//        user.setId(userId);
//        return user;

        return userMapper.selectById(userId);

    }

    @Override
    public String sayHello() {
        return  serviceName + "： hello Dubbo";
    }

    @Override
    public void addUserBatch() {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            User user = new User().setNick("cat" + i)
                    .setPhone("1372001000")
                    .setPassword(UUID.randomUUID().toString())
                    .setEmail("cat0@163.com")
                    .setAccount(String.valueOf(i));
            userMapper.insert(user);
        }

    }
}
