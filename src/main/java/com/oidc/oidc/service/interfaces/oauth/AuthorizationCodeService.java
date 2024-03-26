package com.oidc.oidc.service.interfaces.oauth;

import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
public interface AuthorizationCodeService {
    ResponseEntity<?> createAuthorizationCode(String scope);

    String validateAndGetScope(String code);
}
