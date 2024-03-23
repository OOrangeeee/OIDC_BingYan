package com.oidc.oidc.service.impl.tools;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTool {
    public static final long JWT_TTL = 60 * 60 * 1000L * 24 * 14;//有效期14天

    public static final String JWT_KEY = "SDFGjhdsfalshdfHFdsjkdssdgfhgdsdsffds121232131afasdfac";

    // 生成一个去除了连字符的UUID字符串
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    // 创建JWT
    public static String createJWT(String subject) {
        // 调用getJwtBuilder方法获取JwtBuilder对象，并传入主题和UUID
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());
        return builder.compact();
    }

    // 获取JwtBuilder对象
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        // 如果ttlMillis为空，则使用默认的有效期
        if (ttlMillis == null) {
            ttlMillis = JwtTool.JWT_TTL;
        }
        // 计算过期时间
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        // 返回构建好的JwtBuilder对象
        return Jwts.builder()
                .id(uuid)
                .subject(subject)
                .issuer("sg")
                .issuedAt(now)
                .signWith(secretKey)
                .expiration(expDate);
    }

    // 生成密钥
    public static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(JwtTool.JWT_KEY);
        // 根据解码后的字节数组生成SecretKey对象
        return new SecretKeySpec(encodeKey, 0, encodeKey.length, "HmacSHA256");
    }

    // 解析JWT
    public static Claims parseJWT(String jwt) throws Exception {
        // 生成密钥
        SecretKey secretKey = generalKey();
        // 解析JWT，并返回Claims对象
        return Jwts.parser()
                .verifyWith(secretKey) // 使用密钥验证JWT
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
