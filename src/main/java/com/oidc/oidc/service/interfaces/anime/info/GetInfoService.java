package com.oidc.oidc.service.interfaces.anime.info;

import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
public interface GetInfoService {
    ResponseEntity<?> getInfoOfAnime(int id);
}
