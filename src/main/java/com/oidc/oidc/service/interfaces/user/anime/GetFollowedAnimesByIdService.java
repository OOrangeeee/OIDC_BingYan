package com.oidc.oidc.service.interfaces.user.anime;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author 晋晨曦
 */
public interface GetFollowedAnimesByIdService {
    ResponseEntity<?> getFollowedAnimesById(Map<String,String> mapParams);
}
