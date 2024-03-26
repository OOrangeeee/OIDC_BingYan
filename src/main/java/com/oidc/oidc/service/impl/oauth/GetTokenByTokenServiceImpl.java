package com.oidc.oidc.service.impl.oauth;

import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.service.impl.tools.JwtTool;
import com.oidc.oidc.service.interfaces.oauth.GetTokenByTokenService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class GetTokenByTokenServiceImpl implements GetTokenByTokenService {


    @Override
    public String getTokenByToken(String token) throws Exception {
        if (JwtTool.isJwtExpired(token)) {
            return "token已过期";
        }
        Claims claims = JwtTool.parseJwt(token);
        Map<String, Object> accessClaims = new HashMap<>();
        accessClaims.put("id", claims.get("id", Integer.class));
        accessClaims.put("ifNickname", claims.get("ifNickname", Boolean.class));
        accessClaims.put("ifEmail", claims.get("ifEmail", Boolean.class));
        accessClaims.put("ifAvatar", claims.get("ifAvatar", Boolean.class));
        accessClaims.put("ifIntroduction", claims.get("ifIntroduction", Boolean.class));

        return JwtTool.createJwtWithClaims(Integer.toString(claims.get("id", Integer.class)), accessClaims, 5 * 60 * 1000);
    }
}
