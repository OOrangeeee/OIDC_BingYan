package com.oidc.oidc.service.interfaces.user.account;

import org.springframework.http.ResponseEntity;

import java.util.Map;


/**
 * @author 晋晨曦
 */
public interface UserRegisterService {
    ResponseEntity<?> getUserRegister(String userName, String userPassword, String userConfirmPassword, String userEmail, String userNickname, String userAvatar, String userIntroduction);

    ResponseEntity<?> confirmUserAccount(String userConfirmationToken);
}
