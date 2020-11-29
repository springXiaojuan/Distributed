package com.xxj.redis;

import com.xxj.redis.lua.luaTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author xuxiaojuan
 * @date 2020/11/29 9:26 下午
 */
@SpringBootTest
public class LuaTest {


    @Autowired
    private luaTest luaTest;

    @Test
    public void testLuaSet() {
        luaTest.testLuaSet();
    }

    @Test
    public void testLuaGet() {
        luaTest.testLuaGet();
    }

    @Test
    public void testLuaGet2() {
        luaTest.testLuaGet2();
    }


}
