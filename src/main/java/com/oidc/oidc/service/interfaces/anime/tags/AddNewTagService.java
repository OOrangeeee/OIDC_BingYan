package com.oidc.oidc.service.interfaces.anime.tags;

import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
public interface AddNewTagService {
    ResponseEntity<?> addNewTag(String tagName);
}
