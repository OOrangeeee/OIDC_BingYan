package com.oidc.oidc.service.impl.anime.search;

import com.oidc.oidc.mapper.AnimeMapper;
import com.oidc.oidc.pojo.Anime;
import com.oidc.oidc.service.interfaces.anime.search.SearchAnimeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        Map<String, Object> responseBody = new HashMap<>();
        if (name == null || name.trim().isEmpty()) {
            responseBody.put("error_message", "搜索关键词不能为空");
            return ResponseEntity.badRequest().body(responseBody);
        }
        // 使用占位符来防止直接插入
        List<Anime> foundAnimes = animeMapper.findByAnimeNameLike("%" + name + "%");
        if (foundAnimes.isEmpty()) {
            responseBody.put("error_message", "未找到相关动漫");
            return ResponseEntity.badRequest().body(responseBody);
        }

        List<Map<String, Object>> animeList = new ArrayList<>();
        for (Anime anime : foundAnimes) {
            Map<String, Object> animeInfo = new HashMap<>();
            animeInfo.put("id", anime.getId());
            animeInfo.put("name", anime.getAnimeName());
            animeList.add(animeInfo);
        }
        responseBody.put("animes", animeList);
        responseBody.put("message", "查询成功");
        return ResponseEntity.ok(responseBody);
    }

}
