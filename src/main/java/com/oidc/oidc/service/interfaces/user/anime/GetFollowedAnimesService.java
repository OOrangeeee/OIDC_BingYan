package com.oidc.oidc.service.interfaces.user.anime;

import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
public interface GetFollowedAnimesService {
    ResponseEntity<?> getFollowedAnimes();
}
