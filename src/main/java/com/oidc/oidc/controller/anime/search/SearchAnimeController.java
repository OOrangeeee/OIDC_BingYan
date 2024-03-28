package com.oidc.oidc.controller.anime.search;

import com.oidc.oidc.service.interfaces.anime.search.SearchAnimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class SearchAnimeController {
    private final SearchAnimeService searchAnimeService;


    public SearchAnimeController(SearchAnimeService searchAnimeService) {

        this.searchAnimeService = searchAnimeService;
    }

    @GetMapping("/anime/search/")
    public ResponseEntity<?> searchAnime(@RequestParam Map<String, String> mapParams) {
        String animeName = mapParams.get("animeName");
        return searchAnimeService.searchAnimeByName(animeName);
    }
}
