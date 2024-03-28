package com.oidc.oidc.service.interfaces.user_anime;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author 晋晨曦
 */
public interface AddInfoToUserAnimeImpl {
    ResponseEntity<?> addInfoToUserAnime(Map<String, String> mapParams);
}
