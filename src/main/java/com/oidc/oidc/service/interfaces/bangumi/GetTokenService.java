package com.oidc.oidc.service.interfaces.bangumi;

import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
public interface GetTokenService {
    ResponseEntity<?> getToken(String code,String state);
}
