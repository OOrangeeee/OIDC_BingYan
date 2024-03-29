package com.oidc.oidc.service.impl.bangumi;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.BangumiMapper;
import com.oidc.oidc.mapper.StateMapper;
import com.oidc.oidc.pojo.Bangumi;
import com.oidc.oidc.pojo.State;
import com.oidc.oidc.service.impl.client.ClientRegisterServiceImpl;
import com.oidc.oidc.service.interfaces.bangumi.GetTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 晋晨曦
 */
@Service
public class GetTokenServiceImpl implements GetTokenService {
    private final RestTemplate restTemplate;

    private final BangumiMapper bangumiMapper;

    private final StateMapper stateMapper;

    private static final Logger logger = LoggerFactory.getLogger(ClientRegisterServiceImpl.class);

    public GetTokenServiceImpl(RestTemplate restTemplate, BangumiMapper bangumiMapper, StateMapper stateMapper) {
        this.restTemplate = restTemplate;
        this.bangumiMapper = bangumiMapper;
        this.stateMapper = stateMapper;
    }

    @Override
    public ResponseEntity<?> getToken(String code, String state) {
        Map<String, Object> responseBody = new HashMap<>();
        QueryWrapper<State> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state", state);
        State state1 = stateMapper.selectOne(queryWrapper);
        if (state1 == null) {
            responseBody.put("error_message", "state错误");
            logger.error("state错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
        stateMapper.delete(queryWrapper);
        String url = "https://bgm.tv/oauth/access_token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("User-Agent", "OIDC/my-private-project(https://github.com/OOrangeeee/OIDC_BingYan)");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("client_id", "bgm30066606fff09bc2a");
        requestBody.add("client_secret", "bb9f98d59d5b7f9965c217b31782d2f0");
        requestBody.add("code", code);
        requestBody.add("redirect_uri", "http://localhost:714/bangumi/getToken/");
        requestBody.add("state", UUID.randomUUID().toString());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);
        logger.info("Sending request with headers: {}", request.getHeaders());

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        logger.info("Received response: {}", response.getBody());
        String accessToken = (String) response.getBody().get("access_token");
        String refreshToken = (String) response.getBody().get("refresh_token");
        Integer id = bangumiMapper.findMaxId();
        if (id == null) {
            id = 0;
        }
        id++;
        Bangumi newBangumi = new Bangumi(id, accessToken, refreshToken);
        bangumiMapper.insert(newBangumi);
        responseBody.put("error_message", "获得令牌成功");
        return ResponseEntity.ok(responseBody);
    }
}
