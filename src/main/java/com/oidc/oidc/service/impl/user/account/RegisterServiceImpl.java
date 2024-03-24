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

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 晋晨曦
 */
@Service
public class RegisterServiceImpl implements RegisterService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public RegisterServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public Map<String, String> getUserRegister(String userName, String userPassword, String userConfirmPassword, String userNickname, String userEmail, String userAvatar, String userIntroduction) {
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

        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            if (!user.isUserIsActive()) {
                userMapper.deleteById(user.getId());
            } else {
                map.putIfAbsent("error_message", "用户名已存在");
                return map;
            }
        }


        Integer id = userMapper.findMaxId();
        if (id == null) {
            id = 0;
        }

        id++;

        User newUser = new User(id, userName, passwordEncoder.encode(userPassword), userNickname, userEmail, userAvatar, userIntroduction, false, null, null);
        String newUserConfirmationToken = UUID.randomUUID().toString();
        newUserConfirmationToken = id + newUserConfirmationToken + id * id % 23 + id * id % 17;
        newUser.setUserConfirmationToken(newUserConfirmationToken);
        newUser.setUserIsActive(false);
        userMapper.insert(newUser);

        Map<String, String> sendMap = sendConfirmationEmail(newUser);

        if ("发送频率太高，请等待五分钟".equals(sendMap.get("send_email_error_message"))) {
            map.put("error_message", "发送频率太高，请等待五分钟");
        } else {
            map.put("error_message", "注册成功，请前往邮箱激活账号");
        }
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

    private Map<String, String> sendConfirmationEmail(User user) {
        Map<String, String> map = new HashMap<>();
        Date now = new Date();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_email", user.getUserEmail()).orderByDesc("user_last_email_sent_time");
        List<User> users = userMapper.selectList(queryWrapper);

        User userWithLatestEmailTime = users.isEmpty() ? null : users.get(0);

        if (userWithLatestEmailTime != null && userWithLatestEmailTime.getUserLastEmailSentTime() != null) {
            long diff = now.getTime() - userWithLatestEmailTime.getUserLastEmailSentTime().getTime();
            long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff);
            if (diffMinutes < 5) {
                map.put("send_email_error_message", "发送频率太高，请等待五分钟");
                return map;
            }
        }
        for (User userTmp : users) {
            userTmp.setUserLastEmailSentTime(now);
            userMapper.updateById(userTmp);
        }


        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getUserEmail());
        mailMessage.setFrom("Jin0714ForProject@163.com");
        mailMessage.setSubject("看看我！你需要激活！");
        mailMessage.setText("想激活你的账号？点击这里 : " + "http://localhost:714/user/account/confirm?token=" + user.getUserConfirmationToken() + " !!!!!");
        javaMailSender.send(mailMessage);
        map.put("send_email_error_message", "发送成功");
        return map;
    }

}
