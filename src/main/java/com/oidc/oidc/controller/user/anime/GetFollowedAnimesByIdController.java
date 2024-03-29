package com.oidc.oidc.controller.user.anime;

import com.oidc.oidc.service.interfaces.user.anime.GetFollowedAnimesByIdService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class GetFollowedAnimesByIdController {
    private final GetFollowedAnimesByIdService getFollowedAnimesByIdService;

    public GetFollowedAnimesByIdController(GetFollowedAnimesByIdService getFollowedAnimesByIdService) {

        this.getFollowedAnimesByIdService = getFollowedAnimesByIdService;
    }

    @GetMapping("/user/anime/getFollowedAnimesById/")

    public ResponseEntity<?> getFollowedAnimesById(@RequestParam Map<String, String> mapParams) {
        return getFollowedAnimesByIdService.getFollowedAnimesById(mapParams);
    }
}
