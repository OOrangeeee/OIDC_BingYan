package com.oidc.oidc.controller.user.anime;

import com.oidc.oidc.service.interfaces.user.anime.GetFollowedAnimesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 晋晨曦
 */
@RestController
public class GetFollowedAnimesController {
    private final GetFollowedAnimesService getFollowedAnimesService;

    public GetFollowedAnimesController(GetFollowedAnimesService getFollowedAnimesService) {

        this.getFollowedAnimesService = getFollowedAnimesService;
    }

    @GetMapping("/user/anime/getFollowedAnimes/")
    public ResponseEntity<?> getFollowedAnimes() {
        return getFollowedAnimesService.getFollowedAnimes();
    }
}
