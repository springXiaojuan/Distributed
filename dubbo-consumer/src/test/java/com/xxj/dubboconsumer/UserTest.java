package com.xxj.dubboconsumer;

import com.xxj.dubbo.entity.User;
import com.xxj.dubbo.service.IUserService;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author  xuxiaojuan
 * @date  2020/9/24 11:01 上午
 */
@Log4j2
public class UserTest {

    @Autowired
    private  IUserService userService;

    @Test
    public  void testUser() {
        String s = userService.sayHello();
        log.info(s);
        User user = userService.getUser(1L);
        log.info("user === {}",user);
    }
}
