package com.oidc.oidc.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.interfaces.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author 晋晨曦
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> getUserRegister(String userName, String userPassword, String userConfirmPassword, String userEmail, String userNickname, String userAvatar, String userIntroduction) {
        Map<String, String> map = new HashMap<>();

        if (isInvalidField(map, "用户名", userName, 100) ||
                isInvalidField(map, "密码", userPassword, 1000) ||
                isInvalidField(map, "确认密码", userConfirmPassword, 1000) ||
                isInvalidField(map, "邮箱", userEmail, 100) ||
                isInvalidField(map, "昵称", userNickname, 20) ||
                isInvalidField(map, "头像地址", userAvatar, 1000) ||
                isInvalidField(map, "简介", userIntroduction, 1000) ||
                !userPassword.equals(userConfirmPassword)) {
            map.putIfAbsent("error_message", "两次输入的密码不一致");
            return map;
        }

        userName = userName.trim();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        if (!userMapper.selectList(queryWrapper).isEmpty()) {
            map.put("error_message", "该用户名已被注册");
            return map;
        }


        Integer id = userMapper.findMaxId();
        if (id == null) {
            id = 0;
        }

        id++;

        User newUser = new User(id, userName, passwordEncoder.encode(userPassword), userNickname, userEmail, userAvatar, userIntroduction, false, null);
        String newUserConfirmationToken = UUID.randomUUID().toString();
        newUser.setUserConfirmationToken(newUserConfirmationToken);
        newUser.setUserIsActive(false);
        userMapper.insert(newUser);

        sendConfirmationEmail(newUser.getUserEmail(), newUserConfirmationToken);

        map.put("error_message", "注册成功，请前往邮箱激活账号");

        return map;

    }

    @Override
    public Map<String, String> confirmUserAccount(String userConfirmationToken) {
        Map<String, String> map = new HashMap<>();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_confirmation_token", userConfirmationToken);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            map.put("error_message", "无效的确认令牌");
            return map;
        }

        user.setUserIsActive(true);
        // 激活后清除令牌
        user.setUserConfirmationToken(null);
        userMapper.updateById(user);

        map.put("error_message", "账户激活成功");
        return map;
    }

    private boolean isInvalidField(Map<String, String> errorMap, String fieldName, String fieldValue, int maxLength) {
        if (fieldValue == null) {
            errorMap.put("error_message", fieldName + "不能为空");
            return true;
        }
        if (fieldValue.length() > maxLength) {
            errorMap.put("error_message", fieldName + "长度不能超过" + maxLength);
            return true;
        }
        return false;
    }

    @Autowired
    private JavaMailSender javaMailSender;

    private void sendConfirmationEmail(String userEmail, String confirmationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setFrom("Jin0714ForProject@163.com");
        mailMessage.setSubject("看看我！你需要激活！");
        mailMessage.setText("想激活你的账号？点击这里 : " + "http://localhost:714/user/account/confirm?token=" + confirmationToken + " !!!!!");
        javaMailSender.send(mailMessage);
    }

}
