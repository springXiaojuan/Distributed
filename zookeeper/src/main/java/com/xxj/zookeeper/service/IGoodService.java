package com.xxj.zookeeper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxj.zookeeper.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @author  xuxiaojuan
 * @date  2020/8/31 10:06 下午
 */
@Component
public interface IGoodService extends IService<Goods> {

    /**
     *
     * @param id
     * @return
     * @throws java.io.FileNotFoundException
     * @throws InterruptedException
     */
    int updateByPrimaryKeyStore(Long id);
}
