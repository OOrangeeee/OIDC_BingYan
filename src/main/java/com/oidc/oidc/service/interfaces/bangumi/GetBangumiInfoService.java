package com.oidc.oidc.service.interfaces.bangumi;

import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
public interface GetBangumiInfoService {
    ResponseEntity<?> getInfo(String username);
}
