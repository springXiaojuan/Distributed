package com.xxj.dubbo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * @author  xuxiaojuan
 * @date  2020/9/24 10:43 上午
 */
@Getter
@Setter
@TableName(value = "users")
@Accessors(chain = true)
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String nick;

    private String phone;

    private String password;

    private String email;

    private String account;

    private LocalTime createTime;

    private String mark;


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", nick='").append(nick).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", createTime=").append(createTime);
        sb.append(", mark='").append(mark).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
