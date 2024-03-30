package com.oidc.oidc.service.interfaces.user.friend;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author 晋晨曦
 */
public interface AddNewFriendService {
    public ResponseEntity<?> sendNewFriendRequest(Map<String, String> mapParams);

    public ResponseEntity<?> getFriendRequestList();

    public ResponseEntity<?> solveFriendRequest(Map<String, String> mapParams);
}
