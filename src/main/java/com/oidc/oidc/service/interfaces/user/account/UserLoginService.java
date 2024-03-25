package com.oidc.oidc.service.interfaces.user.account;

import java.util.Map;


/**
 * @author 晋晨曦
 */
public interface UserLoginService {
    Map<String,String> getUserToken(String userName, String userPassword);
}
