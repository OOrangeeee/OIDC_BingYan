package com.oidc.oidc.service.interfaces.user_anime;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author 晋晨曦
 */
public interface AddInfoToUserAnimeService {
    ResponseEntity<?> addInfoToUserAnime(Map<String, String> mapParams);
}
