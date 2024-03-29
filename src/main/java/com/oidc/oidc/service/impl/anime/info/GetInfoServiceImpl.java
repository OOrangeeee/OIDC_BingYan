package com.oidc.oidc.service.impl.anime.info;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.AnimeMapper;
import com.oidc.oidc.mapper.FriendsMapper;
import com.oidc.oidc.mapper.UserAnimeMapper;
import com.oidc.oidc.model.UserAnimeInfo;
import com.oidc.oidc.pojo.Anime;
import com.oidc.oidc.pojo.Friends;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.pojo.UserAnime;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.impl.user.account.UserRegisterServiceImpl;
import com.oidc.oidc.service.interfaces.anime.info.GetInfoService;
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
public class GetInfoServiceImpl implements GetInfoService {
    private final AnimeMapper animeMapper;
    private final UserAnimeMapper userAnimeMapper;
    private final FriendsMapper friendsMapper;
    private static final Logger logger = LoggerFactory.getLogger(GetInfoServiceImpl.class);

    public GetInfoServiceImpl(AnimeMapper animeMapper, UserAnimeMapper userAnimeMapper, FriendsMapper friendsMapper) {
        this.animeMapper = animeMapper;
        this.userAnimeMapper = userAnimeMapper;
        this.friendsMapper = friendsMapper;
    }

    @Override
    public ResponseEntity<?> getInfoOfAnime(int id) {
        Map<String, Object> responseBody = new HashMap<>();
        try {
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
                responseBody.put("userSelfAnimeIsFollow", false);
            } else {
                responseBody.put("userSelfAnimeIsFollow", true);
                UserAnimeInfo userAnimeInfo = new UserAnimeInfo(user.getId(), anime.getId(), userAnime.getUserAnimeStatus(), userAnime.getUserAnimeScore(), userAnime.getUserAnimeComment(), userAnime.getUserAnimeTags());
                responseBody.put("userSelfAnimeInfo", userAnimeInfo);
            }
            QueryWrapper<Friends> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("user_id", user.getId());
            List<Friends> friendsList = friendsMapper.selectList(queryWrapper2);
            List<UserAnimeInfo> friendAnimeInfoList = new ArrayList<>();
            for (Friends friends : friendsList) {
                int friendId = friends.getFriendId();
                QueryWrapper<UserAnime> queryWrapper3 = new QueryWrapper<>();
                queryWrapper3.eq("user_id", friendId).eq("anime_id", anime.getId());
                UserAnime friendAnime = userAnimeMapper.selectOne(queryWrapper3);
                if (friendAnime != null) {
                    UserAnimeInfo friendAnimeInfo = new UserAnimeInfo(friendId, anime.getId(), friendAnime.getUserAnimeStatus(), friendAnime.getUserAnimeScore(), friendAnime.getUserAnimeComment(), friendAnime.getUserAnimeTags());
                    friendAnimeInfoList.add(friendAnimeInfo);
                }
            }
            responseBody.put("friendAnimeInfoList", friendAnimeInfoList);
            responseBody.put("animeName", anime.getAnimeName());
            responseBody.put("animeNums", anime.getAnimeNums());
            responseBody.put("animeDirector", anime.getAnimeDirector());
            responseBody.put("animeIntroduction", anime.getAnimeIntroduction());
            responseBody.put("animeUrl", anime.getAnimeUrl());
            responseBody.put("error_message", "读取信息成功");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            logger.error("获取信息失败", e);
            responseBody.put("error_message", "服务异常");
            return ResponseEntity.internalServerError().body(responseBody);
        }
    }
}
