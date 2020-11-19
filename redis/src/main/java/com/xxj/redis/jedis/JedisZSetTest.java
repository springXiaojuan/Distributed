package com.xxj.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * @author xuxiaojuan
 * @date 2020/11/19 5:10 下午
 */
public class JedisZSetTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("10.1.1.240");
        jedis.zadd("zsetK1",1,"aa");
        jedis.zadd("zsetK1",3,"cc");
        jedis.zadd("zsetK1",2,"bb");


//        Double zscore = jedis.zscore("zsetK1", "cc");
//        System.out.println(zscore);
//
//        Double zincrby = jedis.zincrby("zsetK1", 2, "aa");
//        System.out.println(zincrby);
//
//        Double zscore1 = jedis.zscore("zsetK1", "aa");
//        System.out.println(zscore1);

//        Long zsetK11 = jedis.zcard("zsetK1");
//        System.out.println(zsetK11);

//        Long zsetK12 = jedis.zcount("zsetK1", "3", "4");
//        System.out.println(zsetK12);

//        Long zrank = jedis.zrank("zsetK1", "aa");
//        System.out.println(zrank);
//
//        Long zrem = jedis.zrem("zsetK1", "aa");
//        System.out.println(zrem);

//        Long zsetK1 = jedis.zremrangeByRank("zsetK1", 0,1);
//        System.out.println(zsetK1);

        System.out.println("================");
        Set<Tuple> zsetK11 = jedis.zrevrangeByScoreWithScores("zsetK1", 3, 1);
        zsetK11.stream().forEach(r -> {
            System.out.println(r);
        });


    }

}
