package com.oidc.oidc.service.impl.user.account;

import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.interfaces.user.account.InfoService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class InfoServiceImpl implements InfoService {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public InfoServiceImpl(PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public Map<String, String> getUserInfo() {
        Map<String, String> map = new HashMap<>();
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
            User user = loginUser.getUser();

            map.put("error_message", "读取信息成功");
            map.put("id", user.getId().toString());
            map.put("userName", user.getUserName());
            map.put("userNickname", user.getUserNickname());
            map.put("userEmail", user.getUserEmail());
            map.put("userAvatar", user.getUserAvatar());
            map.put("userIntroduction", user.getUserIntroduction());
        } catch (Exception e) {
            map.clear();
            map.put("error_message", "读取用户信息过程中出现错误");
        }
        return map;
    }


    @Override
    public Map<String, String> setUserPassword(String userNowPassword, String userNewPassword, String userNewConfirmPassword) {
        Map<String, String> map = new HashMap<>();
        if (userNowPassword == null) {
            map.put("error_message", "旧密码不能为空");
            return map;
        }
        if (userNewPassword == null) {
            map.put("error_message", "新密码不能为空");
            return map;
        }
        if (userNewConfirmPassword == null) {
            map.put("error_message", "确认密码不能为空");
            return map;
        }
        if (userNowPassword.equals(userNewPassword)) {
            map.put("error_message", "新密码不能与旧密码相同");
            return map;
        }
        if (!userNewPassword.equals(userNewConfirmPassword)) {
            map.put("error_message", "两次输入的密码不一致");
            return map;
        }
        if (userNewPassword.length() > 1000) {
            map.put("error_message", "密码长度不能超过1000");
            return map;
        }
        if (userNewPassword.length() < 6) {
            map.put("error_message", "密码长度不能小于6");
            return map;
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
            User user = loginUser.getUser();

            if (!passwordEncoder.matches(userNowPassword, user.getUserPassword())) {
                map.put("error_message", "当前密码不正确");
                return map;
            }

            user.setUserPassword(passwordEncoder.encode(userNewPassword));

            userMapper.updateById(user);

            map.put("error_message", "修改密码成功");
        } catch (Exception e) {
            map.put("error_message", "修改密码过程中出现错误");
        }

        return map;
    }


    @Override
    public Map<String, String> setUserNickname(String userNewNickname) {
        Map<String, String> map = new HashMap<>();
        if (userNewNickname == null || userNewNickname.trim().isEmpty()) {
            map.put("error_message", "昵称不能为空");
            return map;
        }
        userNewNickname = userNewNickname.trim();
        if (userNewNickname.length() > 20) {
            map.put("error_message", "昵称长度不能超过20");
            return map;
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
            User user = loginUser.getUser();
            user.setUserNickname(userNewNickname);
            userMapper.updateById(user);
            map.put("error_message", "修改昵称成功");
        } catch (Exception e) {
            map.put("error_message", "修改昵称过程中出现错误");
        }
        return map;
    }

    @Override
    public Map<String, String> setUserAvatar(String userNewAvatar) {
        return null;
    }

    @Override
    public Map<String, String> setUserIntroduction(String userNewIntroduction) {
        Map<String, String> map = new HashMap<>();
        if (userNewIntroduction == null) {
            map.put("error_message", "未导入默认介绍");
            return map;
        }

        if (userNewIntroduction.length() > 1000) {
            map.put("error_message", "介绍长度不能超过1000");
            return map;
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
            User user = loginUser.getUser();
            user.setUserIntroduction(userNewIntroduction);
            userMapper.updateById(user);
            map.put("error_message", "修改简介成功");
        } catch (Exception e) {
            map.put("error_message", "修改简介过程中出现错误");
        }
        return map;
    }
}
