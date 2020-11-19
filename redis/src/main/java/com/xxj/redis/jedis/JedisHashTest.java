package com.xxj.redis.jedis;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;

/**
 * @author xuxiaojuan
 * @date 2020/11/19 11:46 上午
 */
public class JedisHashTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("10.1.1.240");
//        // 创建一个新的 hash 并赋值的时候会返回 1
//        Long hset = jedis.hset("hashK1", "1", "K1");
//        System.out.println(hset);
//        // 在已有的 hash 并赋值的时候会返回 0
//        Long hset2 = jedis.hset("hashK1", "1", "K1221");
//        System.out.println(hset2);

//        Long hsetnx = jedis.hsetnx("hashK1", "hashK2", "V222");
//        System.out.println(hsetnx);
//
//        Long hsetnx1 = jedis.hsetnx("hashK1", "hashK2", "V23333");
//        System.out.println(hsetnx1);

        Boolean hexists = jedis.hexists("hashK1", "hashK2");
        System.out.println(hexists);
        Boolean hexists2 = jedis.hexists("hashK1", "hashK2111");
        System.out.println(hexists2);

        // 删除的返回值都是返回1 如果这个filed不存在，则会被忽略
//        Long hdel = jedis.hdel("hashK1", "hashK2");
//        System.out.println(hdel);
//        Long hdel2 = jedis.hdel("hashK1", "hashK333");
//        System.out.println(hdel);

        Long hashK1 = jedis.hlen("hashK1");
        System.out.println(hashK1);

        Long hstrlen = jedis.hstrlen("hashK1", "hashK2");
        System.out.println(hstrlen);

        Long hstrlen2 = jedis.hstrlen("hashK1", "hashK2111");
        System.out.println(hstrlen2);

        List<String> hashK11 = jedis.hvals("hashK1");
        hashK11.stream().forEach(r -> System.out.println(r));


        Map<String, String> hashK12 = jedis.hgetAll("hashK1");
        System.out.println(hashK12);

    }

}
