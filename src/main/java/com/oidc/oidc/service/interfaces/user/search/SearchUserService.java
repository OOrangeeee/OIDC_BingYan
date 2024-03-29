package com.oidc.oidc.service.interfaces.user.search;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author 晋晨曦
 */
public interface SearchUserService {
    ResponseEntity<?> searchUser(Map<String,String> mapParams);
}
