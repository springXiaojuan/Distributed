package com.xxj.zookeeper.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxj.zookeeper.entity.Goods;
import com.xxj.zookeeper.lock.ZookeeperLock;
import com.xxj.zookeeper.mapper.GoodServiceMapper;
import com.xxj.zookeeper.service.IGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author  xuxiaojuan
 * @date  2020/9/11 3:35 下午
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodServiceMapper, Goods> implements IGoodService {

    @Autowired
    private GoodServiceMapper goodServiceMapper;

    private ZookeeperLock lock = new ZookeeperLock("storeLock");

    @Override
    public int updateByPrimaryKeyStore(Long id) {

        System.out.println("开始减库存");
        int update = 0;

        try {
            lock.lock();
            Goods goods = goodServiceMapper.selectById(id);
            System.out.println("当前的库存： " + goods.getNum());
            if(goods.getNum() > 0) {
                update = goodServiceMapper.updateGoodsInventoryById(id);
                if(update > 0) {
                    System.out.println("减库存成功，可以下订单");
                } else {
                    System.out.println("减库存失败，不能下订单");
                    throw new RuntimeException();
                }
            } else {
                System.out.println("库存不足");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unLock();
        }

        return update;
    }

}
