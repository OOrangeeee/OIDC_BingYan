package com.oidc.oidc.service.impl.user_anime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.AnimeMapper;
import com.oidc.oidc.mapper.UserAnimeMapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.Anime;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.pojo.UserAnime;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.impl.user.account.UserRegisterServiceImpl;
import com.oidc.oidc.service.interfaces.user_anime.IfAlreadyFollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class IfAlreadyFollowServiceImpl implements IfAlreadyFollowService {
    private static final Logger logger = LoggerFactory.getLogger(IfAlreadyFollowServiceImpl.class);
    private final UserMapper userMapper;
    private final AnimeMapper animeMapper;
    private final UserAnimeMapper userAnimeMapper;

    public IfAlreadyFollowServiceImpl(UserMapper userMapper, AnimeMapper animeMapper, UserAnimeMapper userAnimeMapper) {

        this.userMapper = userMapper;
        this.animeMapper = animeMapper;
        this.userAnimeMapper = userAnimeMapper;
    }

    @Override
    public ResponseEntity<?> ifAlreadyFollow(Map<String, String> mapParams) {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User userNow = loginUser.getUser();
        Integer userId = userNow.getId();
        Integer animeId = Integer.parseInt(mapParams.get("animeId"));
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("id", userId));
        Anime anime = animeMapper.selectOne(new QueryWrapper<Anime>().eq("id", animeId));
        if (user == null || anime == null || userId == null || animeId == null) {
            responseBody.put("error_message", "参数错误");
            logger.error("参数错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
        UserAnime userAnime = userAnimeMapper.selectOne(new QueryWrapper<UserAnime>().eq("user_id", userId).eq("anime_id", animeId));
        if (userAnime == null) {
            responseBody.put("error_message", "未关注");
        } else {
            responseBody.put("error_message", "已关注");
        }
        return ResponseEntity.ok(responseBody);
    }
}
