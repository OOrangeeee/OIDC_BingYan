package com.oidc.oidc.service.interfaces.anime.add;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author 晋晨曦
 */
public interface AnimeAddService {
    public ResponseEntity<?> addAnime(Map<String, String> mapParams);
}
