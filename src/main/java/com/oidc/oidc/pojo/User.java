package com.oidc.oidc.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String user_name;
    private String user_password;
    private String user_nickname;
    private String user_email;
    private String user_avatar;
    private String user_introduction;
    //省略基础的get。。等等函数，省略基础的无参和有参构造函数，简化代码。
    //后续实现所有功能后如果有时间我再回来自己写。
    //TODO:实现自己的User get等函数。
}
