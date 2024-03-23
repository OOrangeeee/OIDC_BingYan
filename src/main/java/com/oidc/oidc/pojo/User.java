package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChenXi Jin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableField("id")
    private Integer id;
    @TableField("user_name")
    private String userName;
    @TableField("user_password")
    private String userPassword;
    @TableField("user_nickname")
    private String userNickname;
    @TableField("user_email")
    private String userEmail;
    @TableField("user_avatar")
    private String userAvatar;
    @TableField("user_introduction")
    private String userIntroduction;
    //省略基础的get。。等等函数，省略基础的无参和有参构造函数，简化代码。
    //后续实现所有功能后如果有时间我再回来自己写。
    //TODO:实现自己的User get等函数。
}
