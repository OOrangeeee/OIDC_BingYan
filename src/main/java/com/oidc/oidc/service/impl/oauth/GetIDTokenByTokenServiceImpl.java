package com.oidc.oidc.service.impl.oauth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.tools.JwtTool;
import com.oidc.oidc.service.interfaces.oauth.GetIDTokenByTokenService;
import com.oidc.oidc.service.interfaces.oauth.GetTokenByTokenService;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class GetIDTokenByTokenServiceImpl implements GetIDTokenByTokenService {

    private final GetTokenByTokenService getTokenByTokenService;

    private final UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(GetIDTokenByTokenServiceImpl.class);


    public GetIDTokenByTokenServiceImpl(GetTokenByTokenService getTokenByTokenService, UserMapper userMapper) {

        this.userMapper = userMapper;
        this.getTokenByTokenService = getTokenByTokenService;
    }

    @Override
    public ResponseEntity<?> getIdTokenByToken(String accessToken, String refreshToken) throws Exception {
        Map<String, String> responseBody = new HashMap<>();
        if (!(JwtTool.isJwtFormat(accessToken) && JwtTool.isJwtFormat(refreshToken))) {
            responseBody.put("error_message", "令牌格式错误");
            logger.info("令牌格式错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (JwtTool.isJwtExpired(accessToken)) {
            accessToken = getTokenByTokenService.getTokenByToken(refreshToken);
            logger.info("accessToken已刷新");
            if ("token已过期".equals(accessToken)) {
                responseBody.put("error_message", "令牌已过期");
                logger.info("令牌已过期");
                return ResponseEntity.badRequest().body(responseBody);
            }
        }
        try {
            Claims claims = JwtTool.parseJwt(accessToken);
            Map<String, Object> accessClaims = new HashMap<>();
            accessClaims.put("id", claims.get("id", Integer.class));
            accessClaims.put("ifNickname", claims.get("ifNickname", Boolean.class));
            accessClaims.put("ifEmail", claims.get("ifEmail", Boolean.class));
            accessClaims.put("ifAvatar", claims.get("ifAvatar", Boolean.class));
            accessClaims.put("ifIntroduction", claims.get("ifIntroduction", Boolean.class));
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id", accessClaims.get("id"));
            User user = userMapper.selectOne(queryWrapper);
            Map<String, Object> userInfo = new HashMap<>();
            if (user == null) {
                responseBody.put("error_message", "用户不存在");
                logger.info("用户不存在");
                return ResponseEntity.badRequest().body(responseBody);
            }
            if ((Boolean) accessClaims.get("ifNickname")) {
                userInfo.put("userNickname", user.getUserNickname());
            }
            if ((Boolean) accessClaims.get("ifEmail")) {
                userInfo.put("userEmail", user.getUserEmail());
            }
            if ((Boolean) accessClaims.get("ifAvatar")) {
                userInfo.put("userAvatar", user.getUserAvatar());
            }
            if ((Boolean) accessClaims.get("ifIntroduction")) {
                userInfo.put("userIntroduction", user.getUserIntroduction());
            }
            String idToken = JwtTool.createJwtWithClaims(Integer.toString(user.getId()), userInfo, 5 * 60 * 1000);
            responseBody.put("error_message", "获取用户信息成功");
            responseBody.put("idToken", idToken);
            responseBody.put("accessToken", accessToken);
            responseBody.put("refreshToken", refreshToken);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error_message", "令牌无效");
            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
