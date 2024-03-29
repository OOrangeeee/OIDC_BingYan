package com.oidc.oidc.service.impl.user.anime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.FriendsMapper;
import com.oidc.oidc.mapper.UserAnimeMapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.Friends;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.pojo.UserAnime;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.impl.user.account.UserRegisterServiceImpl;
import com.oidc.oidc.service.interfaces.user.anime.GetFollowedAnimesByIdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class GetFollowedAnimesByIdServiceImpl implements GetFollowedAnimesByIdService {

    private static final Logger logger = LoggerFactory.getLogger(GetFollowedAnimesByIdServiceImpl.class);
    private final UserMapper userMapper;
    private final UserAnimeMapper userAnimeMapper;

    private final FriendsMapper friendsMapper;

    public GetFollowedAnimesByIdServiceImpl(UserMapper userMapper, UserAnimeMapper userAnimeMapper, FriendsMapper friendsMapper) {
        this.userMapper = userMapper;
        this.userAnimeMapper = userAnimeMapper;
        this.friendsMapper = friendsMapper;
    }

    @Override
    public ResponseEntity<?> getFollowedAnimesById(Map<String, String> mapParams) {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        try {
            Integer searchId = Integer.parseInt(mapParams.get("searchId"));
            QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", searchId);
            User searchUser = userMapper.selectOne(queryWrapper1);
            if (searchUser == null) {
                responseBody.put("error_message", "参数错误");
                logger.error("参数错误");
                return ResponseEntity.badRequest().body(responseBody);
            }
            QueryWrapper<Friends> queryWrapperFriends = new QueryWrapper<>();
            queryWrapperFriends.eq("user_id", user.getId()).eq("friend_id", searchUser.getId());
            Friends friends = friendsMapper.selectOne(queryWrapperFriends);
            if (friends == null) {
                responseBody.put("error_message", "不是好友，无法查询");
                return ResponseEntity.badRequest().body(responseBody);
            }
            QueryWrapper<UserAnime> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("user_id", searchUser.getId());
            List<UserAnime> userAnimes = userAnimeMapper.selectList(queryWrapper2);
            List<Integer> animeIds = new ArrayList<>();
            for (UserAnime userAnime : userAnimes) {
                animeIds.add(userAnime.getAnimeId());
            }
            responseBody.put("error_message", "查询成功");
            responseBody.put("anime_ids", animeIds);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error_message", "参数错误");
            logger.error("参数错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
