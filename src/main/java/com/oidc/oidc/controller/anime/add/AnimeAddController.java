package com.oidc.oidc.controller.anime.add;

import com.oidc.oidc.service.interfaces.anime.add.AnimeAddService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class AnimeAddController {

    private final AnimeAddService animeAddService;

    public AnimeAddController(AnimeAddService animeAddService) {

        this.animeAddService = animeAddService;
    }

    @PostMapping("/anime/add/")
    public ResponseEntity<?> addAnime(@RequestParam Map<String, String> maoParams) {
        return animeAddService.addAnime(maoParams);
    }
}
