package com.xxj.dubbo.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author  xuxiaojuan
 * @date  2020/9/24 10:43 上午
 */
@Getter
@Setter
public class User implements Serializable {

    private Long id;

    private String userName;

    private Integer age;

    private Integer sex;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", age=").append(age);
        sb.append(", sex=").append(sex);
        sb.append('}');
        return sb.toString();
    }
}
