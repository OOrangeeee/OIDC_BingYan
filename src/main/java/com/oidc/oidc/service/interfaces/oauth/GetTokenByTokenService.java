package com.oidc.oidc.service.interfaces.oauth;

/**
 * @author 晋晨曦
 */
public interface GetTokenByTokenService {
    String getTokenByToken(String token) throws Exception;
}
