package com.oidc.oidc.service.interfaces.user.account;

import java.util.Map;



/**
 * @author 晋晨曦
 */
public interface InfoService {
    Map<String, String> getUserInfo();

    Map<String,String> setUserPassword(int userId,String userPassword,String userConfirmPassword);

    Map<String,String> setUserNickname(int userId,String userNickname);

    Map<String,String> setUserEmail(int userId,String userEmail);

    Map<String,String> setUserAvatar(int userId,String userAvatar);

    Map<String,String> setUserIntroduction(int userId,String userIntroduction);
}
