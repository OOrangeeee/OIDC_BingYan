package com.oidc.oidc.service.impl.anime.tags;

import com.oidc.oidc.mapper.AnimeTagMapper;
import com.oidc.oidc.pojo.AnimeTag;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.impl.user.account.UserRegisterServiceImpl;
import com.oidc.oidc.service.interfaces.anime.tags.AddNewTagService;
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
public class AddNewTagServiceImpl implements AddNewTagService {

    private final AnimeTagMapper animeTagMapper;
    private static final Logger logger = LoggerFactory.getLogger(AddNewTagServiceImpl.class);

    public AddNewTagServiceImpl(AnimeTagMapper animeTagMapper) {

        this.animeTagMapper = animeTagMapper;
    }

    @Override
    public ResponseEntity<?> addNewTag(String tagName) {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        if (!user.isUserIfAdministrator()) {
            responseBody.put("error_message", "无管理员权限");
            logger.error("无管理员权限");
            return ResponseEntity.badRequest().body(responseBody);
        }
        String[] names = tagName.split(",");
        Integer id = animeTagMapper.findMaxId();
        for (String name : names) {
            if (id == null) {
                id = 0;
            }
            id++;
            AnimeTag animeTag = new AnimeTag(id, name);
            animeTagMapper.insert(animeTag);
        }
        responseBody.put("error_message", "添加成功");
        return ResponseEntity.ok(responseBody);
    }
}
