package com.oidc.oidc.service.impl.oauth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.ClientMapper;
import com.oidc.oidc.pojo.Client;
import com.oidc.oidc.service.impl.user.account.UserInfoServiceImpl;
import com.oidc.oidc.service.interfaces.oauth.VerifyClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class VerifyClientServiceImpl implements VerifyClientService {

    private final ClientMapper clientMapper;

    private static final Logger logger = LoggerFactory.getLogger(VerifyClientServiceImpl.class);

    public VerifyClientServiceImpl(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }

    @Override
    public ResponseEntity<?> verifyClient(String clientName, String clientPassword, String clientRedirectionUrl, String state) {
        Map<String, Object> responseBody = new HashMap<>();
        QueryWrapper<Client> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_name", clientName);
        Client client = clientMapper.selectOne(queryWrapper);

        if (clientName == null) {
            responseBody.put("error_message", "客户端名称为空");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (clientPassword == null) {
            responseBody.put("error_message", "客户端密码为空");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (clientRedirectionUrl == null) {
            responseBody.put("error_message", "客户端重定向URL为空");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (client == null) {
            responseBody.put("error_message", "客户端未注册");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (!client.getClientPassword().equals(clientPassword)) {
            responseBody.put("error_message", "客户端密码错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (!client.getClientRedirectionUrl().equals(clientRedirectionUrl)) {
            responseBody.put("error_message", "客户端重定向地址错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (!client.isClientIsActive()) {
            responseBody.put("error_message", "客户端未激活");
            return ResponseEntity.badRequest().body(responseBody);
        }
        client.setClientState(state);
        clientMapper.updateById(client);
        responseBody.put("error_message", "客户端验证通过");
        responseBody.put("clientName", client.getClientName());
        return ResponseEntity.ok(responseBody);

    }
}
