package com.oidc.oidc.service.interfaces.oauth;

import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
public interface TokenByCodeService {
    ResponseEntity<?> generateTokens(String code);
}
