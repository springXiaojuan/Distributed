package com.xxj.redis.service.impl;

import com.xxj.redis.distributeLock.jedis.JedisLock;
import com.xxj.redis.entity.Goods;
import com.xxj.redis.mapper.GoodServiceMapper;
import com.xxj.redis.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 4:46 下午
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodServiceMapper goodServiceMapper;

    @Autowired
    private JedisLock jedisLock;

    private static String LOCK_NAME = "updateGoodStore";


    @Override
    public void updateGoodStore(Long id) {
        String uniqueId = null;
        try {
            uniqueId = jedisLock.lock(LOCK_NAME, 3000L, 3000L);
            if(uniqueId != null) {
                Goods goods = goodServiceMapper.selectById(id);
                if(goods.getNum() > 0) {
                    // 可以减库存
                    int i = goodServiceMapper.updateGoodsInventoryById(id);
                    if(i > 0) {
                        System.out.println("减库存成功");
                    } else {
                        System.out.println("减库存失败");
                    }

                } else {
                    System.out.println("没有库存");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if(uniqueId != null) {
                jedisLock.releaseRedisLock(LOCK_NAME,uniqueId);
            }
        }

    }
}
