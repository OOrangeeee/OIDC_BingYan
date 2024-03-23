package com.oidc.oidc.service.interfaces.user.account;

import java.util.Map;


/**
 * @author ChenXi Jin
 */
public interface RegisterService {
    Map<String, String> getUserRegister(String username, String password, String confirmPassword, String email, String nickname, String avatar, String introduction);//头像和介绍可以为空
}
