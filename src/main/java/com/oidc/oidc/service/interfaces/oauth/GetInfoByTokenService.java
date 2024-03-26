package com.oidc.oidc.service.interfaces.oauth;

import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
public interface GetInfoByTokenService {
    ResponseEntity<?> getInfoByToken(String accessToken, String refreshToken) throws Exception;
}
