package com.oidc.oidc.service.impl.user.anime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.UserAnimeMapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.pojo.UserAnime;
import com.oidc.oidc.service.impl.user.account.UserRegisterServiceImpl;
import com.oidc.oidc.service.interfaces.user.anime.GetFollowedAnimesByIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class GetFollowedAnimesByIdServiceImpl implements GetFollowedAnimesByIdService {

    private static final Logger logger = LoggerFactory.getLogger(UserRegisterServiceImpl.class);
    private final UserMapper userMapper;
    private final UserAnimeMapper userAnimeMapper;

    public GetFollowedAnimesByIdServiceImpl(UserMapper userMapper, UserAnimeMapper userAnimeMapper) {
        this.userMapper = userMapper;
        this.userAnimeMapper = userAnimeMapper;
    }

    @Override
    public ResponseEntity<?> getFollowedAnimesById(Map<String, String> mapParams) {
        Map<String, Object> responseBody = new HashMap<>();
        User user;
        try {
            Integer id = Integer.parseInt(mapParams.get("id"));
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", id);
            user = userMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            responseBody.put("error_message", "参数错误");
            logger.error("参数错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (user == null) {
            responseBody.put("error_message", "参数错误");
            logger.error("参数错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
        QueryWrapper<UserAnime> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());
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
