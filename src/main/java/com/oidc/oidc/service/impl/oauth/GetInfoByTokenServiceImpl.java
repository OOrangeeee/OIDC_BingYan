package com.oidc.oidc.service.impl.oauth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.tools.JwtTool;
import com.oidc.oidc.service.interfaces.oauth.GetInfoByTokenService;
import com.oidc.oidc.service.interfaces.oauth.GetTokenByTokenService;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class GetInfoByTokenServiceImpl implements GetInfoByTokenService {

    private final GetTokenByTokenService getTokenByTokenService;

    private final UserMapper userMapper;


    public GetInfoByTokenServiceImpl(GetTokenByTokenService getTokenByTokenService, UserMapper userMapper) {

        this.userMapper = userMapper;
        this.getTokenByTokenService = getTokenByTokenService;
    }

    @Override
    public ResponseEntity<?> getInfoByToken(String accessToken, String refreshToken) throws Exception {
        Map<String, String> responseBody = new HashMap<>();
        if (!(JwtTool.isJwtFormat(accessToken) && JwtTool.isJwtFormat(refreshToken))) {
            responseBody.put("error_message", "令牌格式错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (JwtTool.isJwtExpired(accessToken)) {
            accessToken = getTokenByTokenService.getTokenByToken(refreshToken);
            if ("token已过期".equals(accessToken)) {
                responseBody.put("error_message", "令牌已过期");
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
            if (user == null) {
                responseBody.put("error_message", "用户不存在");
                return ResponseEntity.badRequest().body(responseBody);
            }
            if ((Boolean) accessClaims.get("ifNickname")) {
                responseBody.put("userNickname", user.getUserNickname());
            }
            if ((Boolean) accessClaims.get("ifEmail")) {
                responseBody.put("userEmail", user.getUserEmail());
            }
            if ((Boolean) accessClaims.get("ifAvatar")) {
                responseBody.put("userAvatar", user.getUserAvatar());
            }
            if ((Boolean) accessClaims.get("ifIntroduction")) {
                responseBody.put("userIntroduction", user.getUserIntroduction());
            }
            responseBody.put("error_message", "获取用户信息成功");
            responseBody.put("accessToken", accessToken);
            responseBody.put("refreshToken", refreshToken);
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error_message", "令牌无效");
            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
