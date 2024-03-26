package com.oidc.oidc.service.interfaces.oauth;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author 晋晨曦
 */
public interface AuthorizationCodeService {
    ResponseEntity<?> createAuthorizationCode(Map<String,String> map);

    String validateAndGetScope(String code);
}
