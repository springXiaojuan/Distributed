package com.xxj.redis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxj.redis.entity.Goods;
import org.springframework.stereotype.Component;

/**
 * @author xuxiaojuan
 * @date 2020/11/20 4:45 下午
 */
@Component
public interface GoodsService extends IService<Goods> {

    void updateGoodStore(Long id);

}
