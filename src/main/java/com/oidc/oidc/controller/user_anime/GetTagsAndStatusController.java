package com.oidc.oidc.controller.user_anime;

import com.oidc.oidc.service.interfaces.user_anime.GetTagsAndStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 晋晨曦
 */
@RestController
public class GetTagsAndStatusController {
    private final GetTagsAndStatusService getTagsAndStatusService;

    public GetTagsAndStatusController(GetTagsAndStatusService getTagsAndStatusService) {
        this.getTagsAndStatusService = getTagsAndStatusService;
    }

    @GetMapping("/user_anime/getTagsAndStatus/")
    public ResponseEntity<?> getTagsAndStatus() {
        return getTagsAndStatusService.getTagsAndStatus();
    }
}
