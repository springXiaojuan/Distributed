package com.xxj.redis.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * @author  xuxiaojuan
 * @date  2020/8/31 9:54 下午
 */
@Getter
@Setter
public class Goods {

    private Long id;

    private String good;

    private Integer num;

    private Integer enable;

    private Timestamp createTime;
}
