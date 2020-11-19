package com.xxj.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * @author xuxiaojuan
 * @date 2020/11/19 10:12 上午
 */
public class jedisStrTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("10.1.1.240");
//        jedis.set("jedisStr","12344");
//        String jedisStr = jedis.get("jedisStr");
//        System.out.println(jedisStr);

        jedis.setex("expire",10,"sss");

        System.out.println( jedis.get("ss"));

//        jedis.set("jedisStr","55666");

        // 返回给重新设置之前的值
        System.out.println(jedis.getSet("jedisStr","55666"));

        System.out.println(jedis.strlen("jedisStr"));
        System.out.println(jedis.strlen("jedisStr2"));
        // 操作一个不是字符串类型的值会报错
//        System.out.println(jedis.strlen("list"));

//        jedis.lpush("list","value1","value2");
//        jedis.lpush("list","value3","value4");

        System.out.println(jedis.lrange("list",0,4));

        // 在某个字符串中追加sku,并返回追加之后的长度
//        Long append = jedis.append("strK2", "ss");
//        System.out.println(append);
//        Long append2 = jedis.append("strK2", "333");
//        System.out.println(append2);

//        System.out.println(jedis.get("strK2"));
//        // 返回修改后的长度
//        Long setrange = jedis.setrange("strK2", 5, "mppm");
        System.out.println(jedis.get("strK2"));

        String strK2 = jedis.getrange("strK2", 1, 5);
        System.out.println(strK2);
        System.out.println(jedis.get("strK2"));

//        System.out.println(jedis.get("strK3"));
//        Long strK3 = jedis.incr("strK3");
//        System.out.println(strK3);
//        System.out.println(jedis.get("strK2"));

        // incr 操作非数字类型的值 会报错
//        Long strK3 = jedis.incr("strK2");
//        System.out.println(jedis.get("strK2"));
//        System.out.println(jedis.get("strK3"));
//        Long strK3 = jedis.incrBy("strK3", 10);
//        System.out.println(strK3);
//
//        Long strK31 = jedis.decrBy("strK3", 10);
//        System.out.println(strK31);
//        Long strK32 = jedis.decr("strK3");
//        System.out.println(strK32);

//        String mset = jedis.mset("strK3", "55", "strK2", "strK2", "strK4", "V4");
//        System.out.println(mset);
//        List<String> mget = jedis.mget("strK3", "strK2", "strK4");
//        mget.stream().forEach(r -> System.out.println(r));

        // msetnx是个原子性方法，要么都设置成功，要么都不设置成功
        // 如若设置的有已经存在的key,则整体返回0，表示该执行条件没有设置成功
        // 否则返回1 表示该执行条件设置成功
        Long msetnx = jedis.msetnx("strK3", "55", "strK2", "strK2");
        System.out.println(msetnx);

        Long msetnx2 = jedis.msetnx("strK5", "V5");
        System.out.println(msetnx2);

        Long msetnx3 = jedis.msetnx("strK5", "V5","strK6", "V6");
        System.out.println(msetnx3);


    }

}
