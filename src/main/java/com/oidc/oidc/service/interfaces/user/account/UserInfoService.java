package com.oidc.oidc.service.interfaces.user.account;

import org.springframework.http.ResponseEntity;



/**
 * @author 晋晨曦
 */
public interface UserInfoService {
    ResponseEntity<?> getUserInfo();
    ResponseEntity<?> setUserPassword(String userNowPassword, String userNewPassword, String userNewConfirmPassword);
    ResponseEntity<?> setUserNickname(String userNewNickname);
    ResponseEntity<?> setUserAvatar(String userNewAvatar);
    ResponseEntity<?> setUserIntroduction(String userNewIntroduction);
}
