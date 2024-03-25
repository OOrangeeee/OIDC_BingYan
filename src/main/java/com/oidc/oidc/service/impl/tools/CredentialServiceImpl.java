package com.oidc.oidc.service.impl.tools;

import com.oidc.oidc.service.interfaces.tools.CredentialService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 晋晨曦
 */
@Service
public class CredentialServiceImpl implements CredentialService {

    @Override
    public Map<String, String> generateCredentials() {
        String id = UUID.randomUUID().toString();
        String password = generateRandomPassword(id.length());
        Map<String, String> credentials = new HashMap<>();
        credentials.put("id", id);
        credentials.put("password", password);
        return credentials;
    }

    private String generateRandomPassword(int length) {
        // 定义密码字符集
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            password.append(chars.charAt(index));
        }
        return password.toString();
    }
}
