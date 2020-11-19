package com.xxj.dubbo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxj.dubbo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author  xuxiaojuan
 * @date  2020/10/19 9:59 上午
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
