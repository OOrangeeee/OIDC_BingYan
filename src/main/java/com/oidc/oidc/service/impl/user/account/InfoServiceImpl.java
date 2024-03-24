package com.oidc.oidc.service.impl.user.account;

import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.interfaces.user.account.InfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class InfoServiceImpl implements InfoService {

    @Override
    public Map<String, String> getUserInfo() {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();
        map.put("error_message", "读取信息成功");
        map.put("id", user.getId().toString());
        map.put("userName", user.getUserName());
        map.put("userNickame", user.getUserNickname());
        map.put("userEmail", user.getUserEmail());
        map.put("userAvatar", user.getUserAvatar());
        map.put("userIntroduction", user.getUserIntroduction());

        return map;

    }

    @Override
    public Map<String, String> setUserPassword(int userId, String userPassword, String userConfirmPassword) {
        return null;
    }

    @Override
    public Map<String, String> setUserNickname(int userId, String userNickname) {
        return null;
    }

    @Override
    public Map<String, String> setUserEmail(int userId, String userEmail) {
        return null;
    }

    @Override
    public Map<String, String> setUserAvatar(int userId, String userAvatar) {
        return null;
    }

    @Override
    public Map<String, String> setUserIntroduction(int userId, String userIntroduction) {
        return null;
    }
}
