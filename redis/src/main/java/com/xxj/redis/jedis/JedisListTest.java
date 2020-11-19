package com.xxj.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;

import java.util.List;

/**
 * @author xuxiaojuan
 * @date 2020/11/19 3:30 下午
 */
public class JedisListTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("10.1.1.240");

//        jedis.lpush("ListK1","qq","ww");
//        jedis.rpush("ListK1","pp","oo");
//
//        jedis.lpush("ListK2","11","22");
//        jedis.rpush("ListK2","33","44");
        System.out.println();
        Long listK1 = jedis.llen("ListK1");
        Long listK2 = jedis.llen("ListK2");

//        for (int i = 0; i < listK1; i++) {
//            String listK11 = jedis.lpop("ListK1");
//            System.out.println(listK11);
//        }

//         for (int i = 0; i < listK1; i++) {
//            String listK11 = jedis.rpop("ListK1");
//            System.out.println(listK11);
//        }

//        jedis.rpoplpush("ListK1","ListK2");
//        jedis.brpoplpush("ListK1","ListK2");

//        Long lrem = jedis.lrem("ListK2", 1, "oo");
//        System.out.println(lrem);

//        String listK11 = jedis.lindex("ListK1", 1);
//        System.out.println(listK11);

//        // 返回的是整个key 的长度
//        Long linsert = jedis.linsert("ListK1", ListPosition.AFTER, "pp", "oo");
//        System.out.println(linsert);
//
//        // 不成功返回 -1
//        Long linsert2 = jedis.linsert("ListK1", ListPosition.AFTER, "pp2", "oo");
//        System.out.println(linsert2);

        String lset = jedis.lset("ListK1", 0, "ww1");
        System.out.println(lset);


        System.out.println("========================");
        List<String> list = jedis.lrange("ListK1", 0, listK1);
        list.stream().forEach(r -> System.out.println(r));
        System.out.println("========================");
        List<String> list2 = jedis.lrange("ListK2", 0, listK2);
        list2.stream().forEach(r -> System.out.println(r));

    }

}
