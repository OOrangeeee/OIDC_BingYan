package com.oidc.oidc.service.impl.anime.info;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.AnimeMapper;
import com.oidc.oidc.mapper.UserAnimeMapper;
import com.oidc.oidc.pojo.Anime;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.pojo.UserAnime;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.interfaces.anime.info.GetInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class GetInfoServiceImpl implements GetInfoService {
    private final AnimeMapper animeMapper;
    private final UserAnimeMapper userAnimeMapper;

    public GetInfoServiceImpl(AnimeMapper animeMapper, UserAnimeMapper userAnimeMapper) {
        this.animeMapper = animeMapper;
        this.userAnimeMapper = userAnimeMapper;
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
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        QueryWrapper<UserAnime> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id", user.getId()).eq("anime_id", anime.getId());
        UserAnime userAnime = userAnimeMapper.selectOne(queryWrapper1);
        if (userAnime == null) {
            responseBody.put("userAnimeIsFollow", false);
        } else {
            responseBody.put("userAnimeIsFollow", true);
            responseBody.put("userAnimeStatus", userAnime.getUserAnimeStatus());
            responseBody.put("userAnimeScore", userAnime.getUserAnimeScore());
            responseBody.put("userAnimeComment", userAnime.getUserAnimeComment());
            responseBody.put("userAnimeTags", userAnime.getUserAnimeTags());
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
