package com.oidc.oidc.service.impl.anime.status;

import com.oidc.oidc.mapper.UserAnimeStatusMapper;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.pojo.UserAnimeStatus;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.impl.user.account.UserRegisterServiceImpl;
import com.oidc.oidc.service.interfaces.anime.status.AddNewStatusService;
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
public class AddNewStatusServiceImpl implements AddNewStatusService {
    private final UserAnimeStatusMapper userAnimeStatusMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserRegisterServiceImpl.class);


    public AddNewStatusServiceImpl(UserAnimeStatusMapper userAnimeStatusMapper) {
        this.userAnimeStatusMapper = userAnimeStatusMapper;
    }

    @Override
    public ResponseEntity<?> addNewStatus(String status) {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        if (!user.isUserIfAdministrator()) {
            responseBody.put("error_message", "无管理员权限");
            logger.error("无管理员权限");
            return ResponseEntity.badRequest().body(responseBody);
        }
        Integer id = userAnimeStatusMapper.findMaxId();
        if (id == null) {
            id = 0;
        }
        id++;
        UserAnimeStatus newUserAnimeStatus = new UserAnimeStatus(id, status);
        userAnimeStatusMapper.insert(newUserAnimeStatus);
        responseBody.put("error_message", "添加成功");
        return ResponseEntity.ok(responseBody);
    }
}
