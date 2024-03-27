package com.oidc.oidc.service.interfaces.oauth;

import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
public interface GetIDTokenByTokenService {
    ResponseEntity<?> getIdTokenByToken(String accessToken, String refreshToken) throws Exception;
}
