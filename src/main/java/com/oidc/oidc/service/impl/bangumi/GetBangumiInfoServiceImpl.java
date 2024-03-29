package com.oidc.oidc.service.impl.bangumi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.BangumiMapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.Bangumi;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.anime.tags.AddNewTagServiceImpl;
import com.oidc.oidc.service.interfaces.bangumi.GetBangumiInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 晋晨曦
 */
@Service
public class GetBangumiInfoServiceImpl implements GetBangumiInfoService {
    private final RestTemplate restTemplate;
    private final UserMapper userMapper;
    private final BangumiMapper bangumiMapper;

    private static final Logger logger = LoggerFactory.getLogger(GetBangumiInfoServiceImpl.class);

    public GetBangumiInfoServiceImpl(RestTemplate restTemplate, UserMapper userMapper, BangumiMapper bangumiMapper) {
        this.restTemplate = restTemplate;
        this.userMapper = userMapper;
        this.bangumiMapper = bangumiMapper;
    }

    @Override
    public ResponseEntity<?> getInfo(String username) {
        Map<String, Object> responseBody = new HashMap<>();
        String url = "https://bgm.tv/user/" + username;
        QueryWrapper<Bangumi> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bangumi_user_id", username);
        Bangumi bangumi = bangumiMapper.selectOne(queryWrapper);
        logger.info("bangumi:{}", bangumi);
        if (bangumi == null) {
            responseBody.put("error_message", "该用户无token");
            return ResponseEntity.badRequest().body(responseBody);
        }
        String accessToken = bangumi.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("User-Agent", "OIDC/my-private-project(https://github.com/OOrangeeee/OIDC_BingYan)");
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        String responseBodyStr = response.getBody();
        String userNickName = null;
        Pattern pattern = Pattern.compile("<div class=\"name\">\\s*<a href=\"/user/[^>]+\">([^<]+)</a>");
        Matcher matcher = pattern.matcher(responseBodyStr);
        if (matcher.find()) {
            userNickName = unicodeEscape(matcher.group(1));
        }
        String userName = null;
        pattern = Pattern.compile("<div class=\"name\">\\s*<a href=\"/user/([^\">]+)\"");
        matcher = pattern.matcher(responseBodyStr);
        if (matcher.find()) {
            userName = unicodeEscape(matcher.group(1));
        }
        String userAvatar = null;
        pattern = Pattern.compile("url\\('([^']+)'\\)");
        matcher = pattern.matcher(responseBodyStr);
        if (matcher.find()) {
            userAvatar = "https:" + matcher.group(1);
        }
        String userIntroduction = null;
        pattern = Pattern.compile("<div class=\"intro\"><blockquote class=\"intro\"><div class=\"bio\">([^<]+)</div></blockquote></div>");
        matcher = pattern.matcher(responseBodyStr);
        if (matcher.find()) {
            userIntroduction = unicodeEscape(matcher.group(1));
        }
        Integer id = userMapper.findMaxId();
        if (id == null) {
            id = 0;
        }
        id++;
        User newUser = new User(id, userName, "null", userNickName, "null", userAvatar, userIntroduction, true, "null", new Date(), false);
        userMapper.insert(newUser);
        responseBody.put("error_message", "成功通过bangumi注册");
        return ResponseEntity.ok().body(responseBody);
    }

    private String unicodeEscape(String input) {
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (c > 127) {
                sb.append("\\u").append(Integer.toHexString(c | 0x10000).substring(1));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
