package com.oidc.oidc.service.interfaces.user.account;

import java.util.Map;


/**
 * @author ChenXi Jin
 */
public interface RegisterService {
    Map<String, String> getUserRegister(String userName, String userPassword, String userConfirmPassword, String userEmail, String userNickname, String userAvatar, String userIntroduction);
}
