package com.oidc.oidc.service.impl.user.anime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.UserAnimeMapper;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.pojo.UserAnime;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.interfaces.user.anime.GetFollowedAnimesService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class GetFollowedAnimesServiceImpl implements GetFollowedAnimesService {
    private final UserAnimeMapper userAnimeMapper;


    public GetFollowedAnimesServiceImpl(UserAnimeMapper userAnimeMapper) {

        this.userAnimeMapper = userAnimeMapper;
    }

    @Override
    public ResponseEntity<?> getFollowedAnimes() {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        Integer userId = user.getId();
        QueryWrapper<UserAnime> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserAnime> userAnimes = userAnimeMapper.selectList(queryWrapper);
        List<Integer> animeIds = new ArrayList<>();
        for (UserAnime userAnime : userAnimes) {
            animeIds.add(userAnime.getAnimeId());
        }
        responseBody.put("error_message", "查询成功");
        responseBody.put("anime_ids", animeIds);
        return ResponseEntity.ok(responseBody);
    }
}
