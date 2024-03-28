package com.oidc.oidc.service.interfaces.anime.search;

import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
public interface SearchAnimeService {
    ResponseEntity<?> searchAnimeByName(String name);
}
