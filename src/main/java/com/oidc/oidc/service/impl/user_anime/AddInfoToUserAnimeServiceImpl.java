package com.oidc.oidc.service.impl.user_anime;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.AnimeTagMapper;
import com.oidc.oidc.mapper.UserAnimeMapper;
import com.oidc.oidc.mapper.UserAnimeStatusMapper;
import com.oidc.oidc.pojo.AnimeTag;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.pojo.UserAnime;
import com.oidc.oidc.pojo.UserAnimeStatus;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.impl.user.account.UserRegisterServiceImpl;
import com.oidc.oidc.service.interfaces.user_anime.AddInfoToUserAnimeService;
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
public class AddInfoToUserAnimeServiceImpl implements AddInfoToUserAnimeService {
    private final UserAnimeMapper userAnimeMapper;
    private final UserAnimeStatusMapper userAnimeStatusMapper;
    private final AnimeTagMapper animeTagMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserRegisterServiceImpl.class);

    public AddInfoToUserAnimeServiceImpl(UserAnimeMapper userAnimeMapper, UserAnimeStatusMapper userAnimeStatusMapper, AnimeTagMapper animeTagMapper) {
        this.animeTagMapper = animeTagMapper;
        this.userAnimeStatusMapper = userAnimeStatusMapper;
        this.userAnimeMapper = userAnimeMapper;
    }

    @Override
    public ResponseEntity<?> addInfoToUserAnime(Map<String, String> mapParams) {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        Integer animeId = Integer.parseInt(mapParams.get("animeId"));
        Integer userId = user.getId();
        String userAnimeStatus = mapParams.get("userAnimeStatus");
        Integer userAnimeScore = Integer.parseInt(mapParams.get("userAnimeScore"));
        String userAnimeComment = mapParams.get("userAnimeComment");
        String userAnimeTags = mapParams.get("userAnimeTags");
        if (animeId == null || userId == null || userAnimeStatus == null) {
            responseBody.put("error_message", "追番必要参数不足");
            return ResponseEntity.badRequest().body(responseBody);
        }
        QueryWrapper<UserAnimeStatus> userAnimeStatusQueryWrapper = new QueryWrapper<>();
        userAnimeStatusQueryWrapper.eq("user_anime_status_info", userAnimeStatus);
        UserAnimeStatus userAnimeStatusInfo = userAnimeStatusMapper.selectOne(userAnimeStatusQueryWrapper);
        if (userAnimeStatusInfo == null) {
            responseBody.put("error_message", "状态不存在");
            logger.error("状态不存在");
            return ResponseEntity.badRequest().body(responseBody);
        }
        String[] tags = userAnimeTags.split(",");

        for (String tag : tags) {
            QueryWrapper<AnimeTag> animeTagQueryWrapper = new QueryWrapper<>();
            animeTagQueryWrapper.eq("anime_tag", tag);
            AnimeTag animeTag = animeTagMapper.selectOne(animeTagQueryWrapper);
            if (animeTag == null) {
                responseBody.put("error_message", "标签不存在");
                logger.error("标签不存在");
                return ResponseEntity.badRequest().body(responseBody);
            }
        }
        if (userAnimeScore > 10 || userAnimeScore < 0) {
            responseBody.put("error_message", "分数不合法");
            logger.error("分数不合法");
            return ResponseEntity.badRequest().body(responseBody);
        }
        UserAnime userAnime = userAnimeMapper.selectOne(new QueryWrapper<UserAnime>().eq("user_id", userId).eq("anime_id", animeId));
        if (userAnime != null) {
            userAnimeMapper.deleteById(userAnime.getId());
        }
        Integer id = userAnimeMapper.findMaxId();
        if (id == null) {
            id = 0;
        }
        id++;
        UserAnime newUserAnime = new UserAnime(id, userId, animeId, userAnimeStatus, userAnimeScore, userAnimeComment, userAnimeTags);
        userAnimeMapper.insert(newUserAnime);
        responseBody.put("error_message", "追番成功");
        return ResponseEntity.ok(responseBody);
    }
}
