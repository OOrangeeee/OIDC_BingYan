package com.oidc.oidc.service.interfaces.user.account;

import org.springframework.http.ResponseEntity;

import java.util.Map;


/**
 * @author 晋晨曦
 */
public interface UserLoginService {
    ResponseEntity<?> getUserToken(String userName, String userPassword);
}
