package com.oidc.oidc.service.interfaces.anime.status;

import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
public interface AddNewStatusService {
    ResponseEntity<?> addNewStatus(String status);
}
