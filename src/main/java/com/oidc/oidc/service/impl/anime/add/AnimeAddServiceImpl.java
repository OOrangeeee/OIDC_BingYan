package com.oidc.oidc.service.impl.anime.add;

import com.oidc.oidc.mapper.AnimeMapper;
import com.oidc.oidc.pojo.Anime;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.impl.user.account.UserRegisterServiceImpl;
import com.oidc.oidc.service.interfaces.anime.add.AnimeAddService;
import org.apache.commons.lang3.StringUtils;
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
public class AnimeAddServiceImpl implements AnimeAddService {

    private final AnimeMapper animeMapper;

    private static final Logger logger = LoggerFactory.getLogger(AnimeAddServiceImpl.class);

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
            logger.error("无管理员权限");
            return ResponseEntity.badRequest().body(responseBody);
        }

        String animeName = mapParams.get("animeName");
        String animeNumsStr = mapParams.get("animeNums");
        String animeDirector = mapParams.get("animeDirector");
        String animeIntroduction = mapParams.get("animeIntroduction");
        String animeUrl = mapParams.get("animeUrl");

        if (StringUtils.isBlank(animeName) || StringUtils.isBlank(animeNumsStr) || !StringUtils.isNumeric(animeNumsStr)
                || StringUtils.isBlank(animeDirector) || StringUtils.isBlank(animeIntroduction) || StringUtils.isBlank(animeUrl)) {
            logger.error("参数不能为空且必须为有效值");
            responseBody.put("error_message", "参数不能为空且必须为有效值");
            return ResponseEntity.badRequest().body(responseBody);
        }
        int animeNums = Integer.parseInt(animeNumsStr);

        Integer id = animeMapper.findMaxId();
        id = (id == null) ? 0 : id;
        id++;

        Anime newAnime = new Anime(id, animeName, animeNums, animeDirector, animeIntroduction, animeUrl);
        animeMapper.insert(newAnime);

        responseBody.put("success_message", "添加动画成功");
        return ResponseEntity.ok(responseBody);
    }

}
