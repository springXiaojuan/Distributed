package com.xxj.redis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxj.redis.entity.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author  xuxiaojuan
 * @date  2020/9/11 3:36 下午
 */
@Mapper
public interface GoodServiceMapper extends BaseMapper<Goods> {
    @Update("update goods set num = num -1 where id = #{id}")
    int updateGoodsInventoryById(@Param("id") Long id) ;
}
