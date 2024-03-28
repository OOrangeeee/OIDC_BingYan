package com.oidc.oidc.service.impl.anime.search;

import com.oidc.oidc.mapper.AnimeMapper;
import com.oidc.oidc.pojo.Anime;
import com.oidc.oidc.service.interfaces.anime.search.SearchAnimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class SearchAnimeServiceImpl implements SearchAnimeService {

    private final AnimeMapper animeMapper;

    public SearchAnimeServiceImpl(AnimeMapper animeMapper) {
        this.animeMapper = animeMapper;
    }

    @Override
    public ResponseEntity<?> searchAnimeByName(String name) {
        List<Anime> foundAnimes = animeMapper.findByAnimeNameLike(name);
        Map<String, Object> responseBody = new HashMap<>();
        if (foundAnimes.isEmpty()) {
            responseBody.put("error_message", "未找到相关动漫");
            return ResponseEntity.badRequest().body(responseBody);
        }
        int count = 1;
        for (Anime anime : foundAnimes) {
            Map<Integer, String> animeMap = new HashMap<>();

            animeMap.put(anime.getId(), anime.getAnimeName());

            responseBody.put(Integer.toString(count), animeMap);

            count++;
        }
        responseBody.put("error_message", "查询成功");
        return ResponseEntity.ok(responseBody);
    }
}
