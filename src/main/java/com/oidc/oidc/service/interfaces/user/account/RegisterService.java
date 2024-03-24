package com.oidc.oidc.service.interfaces.user.account;

import java.util.Map;


/**
 * @author 晋晨曦
 */
public interface RegisterService {
    Map<String, String> getUserRegister(String userName, String userPassword, String userConfirmPassword, String userEmail, String userNickname, String userAvatar, String userIntroduction);

    Map<String, String> confirmUserAccount(String userConfirmationToken);
}
