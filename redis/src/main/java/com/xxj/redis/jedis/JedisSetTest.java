package com.xxj.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * @author xuxiaojuan
 * @date 2020/11/19 4:29 下午
 */
public class JedisSetTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("10.1.1.240");

//        jedis.sadd("setK1","11","22");
//        jedis.sadd("setK2","99","88");
        jedis.sadd("setK3","77","88","66","11","00");

//        Boolean setK2 = jedis.sismember("setK2", "11");
//        System.out.println(setK2);
//
//        Boolean setK3 = jedis.sismember("setK2", "99");
//        System.out.println(setK3);
//
//        String setK21 = jedis.spop("setK2");
//        System.out.println(setK21);

//        String setK2 = jedis.srandmember("setK2");
//        System.out.println(setK2);

//        Long setK21 = jedis.srem("setK2", "990");
//        System.out.println(setK21);
//
//        Long setK22 = jedis.srem("setK2", "99");
//        System.out.println(setK22);

//        Long smove = jedis.smove("setK1", "setK2", "11");
//        System.out.println(smove);
//
//        Long setK2 = jedis.scard("setK2");
//        System.out.println(setK2);


//        Set<String> sinter = jedis.sinter("setK1", "setK2");
//        sinter.stream().forEach(r -> System.out.println(r));

        // 这个命令会把key的交集 保存到目标key中
//        Long sinterstore = jedis.sinterstore("setK1", "setK2","setK3");


//        Set<String> sunion = jedis.sunion("setK1", "setK2", "setK3");
//        sunion.stream().forEach(r -> System.out.println(r));
//        System.out.println("================");
//
//
//        Long sunionstore = jedis.sunionstore("setK1", "setK2", "setK3");
//        System.out.println(sunionstore);

//        Set<String> sdiff = jedis.sdiff("setK1", "setK2");
//        sdiff.stream().forEach(r -> System.out.println(r));

//        Long sdiffstore = jedis.sdiffstore("setK1", "setK2");
//        System.out.println(sdiffstore);

        System.out.println("================");
        Set<String> smembers3 = jedis.smembers("setK3");
        smembers3.stream().forEach(r -> System.out.println(r));

        System.out.println("================");
        Set<String> smembers = jedis.smembers("setK2");
        smembers.stream().forEach(r -> System.out.println(r));

        System.out.println("================");
        Set<String> smembers1 = jedis.smembers("setK1");
        smembers1.stream().forEach(r -> System.out.println(r));


    }

}
