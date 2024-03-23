package com.oidc.oidc.service.interfaces.user.account;

import java.util.Map;


/**
 * @author ChenXi Jin
 */
public interface LoginService {
    Map<String,String> getUserToken(String username, String password);
}
