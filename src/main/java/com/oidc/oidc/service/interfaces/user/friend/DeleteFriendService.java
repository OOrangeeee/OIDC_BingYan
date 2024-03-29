package com.oidc.oidc.service.interfaces.user.friend;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author 晋晨曦
 */
public interface DeleteFriendService {
    ResponseEntity<?> deleteFriend(Map<String, String> mapParams);
}
