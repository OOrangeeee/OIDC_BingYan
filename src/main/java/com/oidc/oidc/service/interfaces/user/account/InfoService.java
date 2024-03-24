package com.oidc.oidc.service.interfaces.user.account;

import java.util.Map;



/**
 * @author 晋晨曦
 */
public interface InfoService {
    Map<String, String> getUserInfo();

    Map<String, String> setUserPassword(String userNowPassword,String userNewPassword, String userNewConfirmPassword);

    Map<String,String> setUserNickname(String userNewNickname);


    Map<String,String> setUserAvatar(String userNewAvatar);

    Map<String,String> setUserIntroduction(String userNewIntroduction);
}
