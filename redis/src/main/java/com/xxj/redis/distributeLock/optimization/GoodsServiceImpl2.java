package com.xxj.redis.distributeLock.optimization;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxj.redis.distributeLock.jedis.JedisLock;
import com.xxj.redis.entity.Goods;
import com.xxj.redis.mapper.GoodServiceMapper;
import com.xxj.redis.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 4:46 下午
 */
@Service
public class GoodsServiceImpl2 extends ServiceImpl<GoodServiceMapper,Goods> implements GoodsService {

    @Autowired
    private GoodServiceMapper goodServiceMapper;

    @Autowired
    private JedisLock jedisLock;

    @Autowired
    private RedisTemplate redisTemplate;

    private static String LOCK_NAME = "updateGoodStore";



    private SegmentDistributeLock  segmentDistributeLock = new SegmentDistributeLock(true);


    @Override
    public void updateGoodStore(Long id) {

        int update = 0;
        boolean flag = true;
        List<String> lockSegmentList = new ArrayList<>();
        while(flag) {
            Map<String, String> lockMap = segmentDistributeLock.lock(LOCK_NAME, 3000L, 3000L, lockSegmentList);
            System.out.println("获取锁结果：" + lockMap);
            if(null != lockMap && lockMap.keySet().size() > 0) {
                try {
                    int store = redisTemplate.opsForValue().get(lockMap.get(SegmentDistributeLock.STORE_KEY)) == null ? 0 : Integer.parseInt(redisTemplate.opsForValue().get(lockMap.get(SegmentDistributeLock.STORE_KEY)).toString());
                    System.out.println("库存 = " + store);

                    if (store > 0) {
                        flag = false;
                        Long leftStore = redisTemplate.opsForValue().decrement(lockMap.get(SegmentDistributeLock.STORE_KEY));
                        if (leftStore >= 0) {
                            System.out.println("减库存成功，可以下订单");
                        } else {
                            System.out.println("减库存失败，不能下订单");
                            throw new RuntimeException();
                        }

                    } else {
                        System.out.println("没有库存");
                        if (segmentDistributeLock.isAllSegmentOver(lockSegmentList)) {
                            System.out.println("没有库存了 ，跳出循环");
                            flag = false;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    segmentDistributeLock.releaseRedisLock(lockMap);
                }
            }
            }

    }
}
