package com.oidc.oidc.service.impl.bangumi;

import com.oidc.oidc.mapper.StateMapper;
import com.oidc.oidc.pojo.State;
import com.oidc.oidc.service.interfaces.bangumi.AuthorizeService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

/**
 * @author 晋晨曦
 */
@Service
public class AuthorizeServiceImpl implements AuthorizeService {
    private final StateMapper stateMapper;

    public AuthorizeServiceImpl(StateMapper stateMapper) {
        this.stateMapper = stateMapper;
    }

    @Override
    public RedirectView authorize() {
        String clientId = "bgm30066606fff09bc2a";
        String responseType = "code";
        String redirectUri = "http://localhost:714/bangumi/getToken/";
        String state = UUID.randomUUID().toString();
        String url = String.format("https://bgm.tv/oauth/authorize?client_id=%s&response_type=%s&redirect_uri=%s&state=%s", clientId, responseType, redirectUri, state);
        Integer id = stateMapper.findMaxId();
        if (id == null) {
            id = 0;
        }
        id++;
        stateMapper.insert(new State(id, state));
        return new RedirectView(url);
    }
}
