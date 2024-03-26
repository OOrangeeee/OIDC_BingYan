package com.oidc.oidc.service.impl.user.account;

import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.client.ClientRegisterServiceImpl;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.interfaces.user.account.UserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class UserInfoServiceImpl implements UserInfoService {

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    public UserInfoServiceImpl(PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<?> getUserInfo() {
        Map<String, Object> responseBody = new HashMap<>();
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
            User user = loginUser.getUser();
            responseBody.put("error_message", "读取信息成功");
            responseBody.put("id", user.getId());
            responseBody.put("userName", user.getUserName());
            responseBody.put("userNickname", user.getUserNickname());
            responseBody.put("userEmail", user.getUserEmail());
            responseBody.put("userAvatar", user.getUserAvatar());
            responseBody.put("userIntroduction", user.getUserIntroduction());
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.clear();
            responseBody.put("error_message", "读取用户信息过程中出现错误");
            logger.error("读取用户信息过程中出现错误", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }


    @Override
    public ResponseEntity<?> setUserPassword(String userNowPassword, String userNewPassword, String userNewConfirmPassword) {
        Map<String, Object> responseBody = new HashMap<>();
        if (userNowPassword == null || userNewPassword == null || userNewConfirmPassword == null) {
            responseBody.put("error_message", "密码不能为空");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (userNowPassword.equals(userNewPassword)) {
            responseBody.put("error_message", "新密码不能与旧密码相同");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (!userNewPassword.equals(userNewConfirmPassword)) {
            responseBody.put("error_message", "两次输入的密码不一致");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (userNewPassword.length() > 1000 || userNewPassword.length() < 6) {
            responseBody.put("error_message", "密码长度应在6到1000字符之间");
            return ResponseEntity.badRequest().body(responseBody);
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
            User user = loginUser.getUser();
            if (!passwordEncoder.matches(userNowPassword, user.getUserPassword())) {
                responseBody.put("error_message", "当前密码不正确");
                return ResponseEntity.badRequest().body(responseBody);
            }
            user.setUserPassword(passwordEncoder.encode(userNewPassword));
            userMapper.updateById(user);
            responseBody.put("success_message", "修改密码成功");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error_message", "修改密码过程中出现错误");
            logger.error("修改密码过程中出现错误", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }


    @Override
    public ResponseEntity<?> setUserNickname(String userNewNickname) {
        Map<String, Object> responseBody = new HashMap<>();
        if (userNewNickname == null || userNewNickname.trim().isEmpty()) {
            responseBody.put("error_message", "昵称不能为空");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (userNewNickname.trim().length() > 20) {
            responseBody.put("error_message", "昵称长度不能超过20");
            return ResponseEntity.badRequest().body(responseBody);
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
            User user = loginUser.getUser();
            user.setUserNickname(userNewNickname.trim());
            userMapper.updateById(user);
            responseBody.put("success_message", "修改昵称成功");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error_message", "修改昵称过程中出现错误");
            logger.error("修改昵称过程中出现错误", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }


    @Override
    public ResponseEntity<?> setUserAvatar(String userNewAvatar) {
        Map<String, Object> responseBody = new HashMap<>();
        if (userNewAvatar == null) {
            responseBody.put("error_message", "头像不能为空");
            return ResponseEntity.badRequest().body(responseBody);
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
            User user = loginUser.getUser();
            user.setUserAvatar(userNewAvatar);
            userMapper.updateById(user);
            responseBody.put("success_message", "修改头像成功");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error_message", "修改头像过程中出现错误");
            logger.error("修改头像过程中出现错误", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }


    @Override
    public ResponseEntity<?> setUserIntroduction(String userNewIntroduction) {
        Map<String, Object> responseBody = new HashMap<>();
        if (userNewIntroduction == null) {
            responseBody.put("error_message", "介绍不能为空");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (userNewIntroduction.length() > 1000) {
            responseBody.put("error_message", "介绍长度不能超过1000");
            return ResponseEntity.badRequest().body(responseBody);
        }
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
            User user = loginUser.getUser();
            user.setUserIntroduction(userNewIntroduction);
            userMapper.updateById(user);
            responseBody.put("success_message", "修改简介成功");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error_message", "修改简介过程中出现错误");
            logger.error("修改简介过程中出现错误", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
        }
    }

}
