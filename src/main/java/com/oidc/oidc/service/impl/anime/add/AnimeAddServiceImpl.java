package com.oidc.oidc.service.impl.anime.add;

import com.oidc.oidc.mapper.AnimeMapper;
import com.oidc.oidc.pojo.Anime;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.interfaces.anime.add.AnimeAddService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class AnimeAddServiceImpl implements AnimeAddService {

    private final AnimeMapper animeMapper;


    public AnimeAddServiceImpl(AnimeMapper animeMapper) {

        this.animeMapper = animeMapper;
    }

    @Override
    public ResponseEntity<?> addAnime(Map<String, String> mapParams) {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        if (!user.isUserIfAdministrator()) {
            responseBody.put("error_message", "无管理员权限");
            return ResponseEntity.badRequest().body(responseBody);
        }
        String animeName = mapParams.get("animeName");
        int animeNums = Integer.parseInt(mapParams.get("animeNums"));
        String animeDirector = mapParams.get("animeDirector");
        String animeIntroduction = mapParams.get("animeIntroduction");
        String animeUrl = mapParams.get("animeUrl");
        Integer id = animeMapper.findMaxId();
        if (id == null) {
            id = 0;
        }
        id++;
        Anime newAnime = new Anime(id, animeName, animeNums, animeDirector, animeIntroduction, animeUrl);
        animeMapper.insert(newAnime);
        responseBody.put("success_message", "添加动画成功");
        return ResponseEntity.ok(responseBody);
    }
}
