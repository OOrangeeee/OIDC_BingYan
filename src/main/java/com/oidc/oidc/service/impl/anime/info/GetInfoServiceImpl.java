package com.oidc.oidc.service.impl.anime.info;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.AnimeMapper;
import com.oidc.oidc.pojo.Anime;
import com.oidc.oidc.service.interfaces.anime.info.GetInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class GetInfoServiceImpl implements GetInfoService {
    private final AnimeMapper animeMapper;

    public GetInfoServiceImpl(AnimeMapper animeMapper) {
        this.animeMapper = animeMapper;
    }

    @Override
    public ResponseEntity<?> getInfoOfAnime(int id) {
        Map<String, Object> responseBody = new HashMap<>();
        QueryWrapper<Anime> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Anime anime = animeMapper.selectOne(queryWrapper);
        if (anime == null) {
            responseBody.put("error_message", "没有找到该番剧");
            return ResponseEntity.badRequest().body(responseBody);
        }
        responseBody.put("animeName", anime.getAnimeName());
        responseBody.put("animeNums", anime.getAnimeNums());
        responseBody.put("animeDirector", anime.getAnimeDirector());
        responseBody.put("animeIntroduction", anime.getAnimeIntroduction());
        responseBody.put("animeUrl", anime.getAnimeUrl());
        responseBody.put("error_message", "读取信息成功");
        return ResponseEntity.ok(responseBody);
    }
}
