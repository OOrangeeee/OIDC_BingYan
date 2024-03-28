package com.oidc.oidc.controller.user_anime;

import com.oidc.oidc.service.interfaces.user_anime.AddInfoToUserAnimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class AddInfoToUserAnimeController {
    private final AddInfoToUserAnimeService addInfoToUserAnimeService;

    public AddInfoToUserAnimeController(AddInfoToUserAnimeService addInfoToUserAnimeService) {

        this.addInfoToUserAnimeService = addInfoToUserAnimeService;
    }

    @PostMapping("/user_anime/addInfoToUserAnime/")
    public ResponseEntity<?> addInfoToUserAnime(@RequestParam Map<String, String> mapParams) {

        return addInfoToUserAnimeService.addInfoToUserAnime(mapParams);
    }
}
