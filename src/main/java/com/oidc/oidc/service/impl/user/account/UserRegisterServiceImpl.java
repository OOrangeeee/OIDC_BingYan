package com.oidc.oidc.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.interfaces.user.account.UserRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class UserRegisterServiceImpl implements UserRegisterService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    private static final Logger logger = LoggerFactory.getLogger(UserRegisterServiceImpl.class);

    public UserRegisterServiceImpl(UserMapper userMapper, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public ResponseEntity<?> getUserRegister(String userName, String userPassword, String userConfirmPassword, String userNickname, String userEmail, String userAvatar, String userIntroduction) {
        Map<String, Object> responseBody = new HashMap<>();

        if (isInvalidField(responseBody, "用户名", userName, 100) ||
                isInvalidField(responseBody, "密码", userPassword, 1000) ||
                isInvalidField(responseBody, "确认密码", userConfirmPassword, 1000) ||
                isInvalidField(responseBody, "邮箱", userEmail, 100) ||
                isInvalidField(responseBody, "昵称", userNickname, 20) ||
                isInvalidField(responseBody, "头像地址", userAvatar, 1000) ||
                isInvalidField(responseBody, "简介", userIntroduction, 1000) ||
                !userPassword.equals(userConfirmPassword)) {
            responseBody.putIfAbsent("error_message", "两次输入的密码不一致");
            return ResponseEntity.badRequest().body(responseBody);
        }

        if (userPassword.length() < 6) {
            responseBody.put("error_message", "密码长度不能小于6位");
            return ResponseEntity.badRequest().body(responseBody);
        }

        userName = userName.trim();
        userNickname = userNickname.trim();


        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);

        List<User> users = userMapper.selectList(queryWrapper);
        for (User user : users) {
            if (!user.isUserIsActive()) {
                userMapper.deleteById(user.getId());
            } else {
                responseBody.putIfAbsent("error_message", "用户名已存在");
                return ResponseEntity.badRequest().body(responseBody);
            }
        }


        Integer id = userMapper.findMaxId();
        if (id == null) {
            id = 0;
        }
        id++;
        User newUser;
        if ("Orange is Cool! bsbflsgxh..".equals(userIntroduction)) {
            newUser = new User(id, userName, passwordEncoder.encode(userPassword), userNickname, userEmail, userAvatar, userIntroduction, false, null, null, true);
        } else {
            newUser = new User(id, userName, passwordEncoder.encode(userPassword), userNickname, userEmail, userAvatar, userIntroduction, false, null, null, false);
        }
        String newUserConfirmationToken = UUID.randomUUID().toString();
        newUserConfirmationToken = id + newUserConfirmationToken + id * id % 23 + id * id % 17;
        newUser.setUserConfirmationToken(newUserConfirmationToken);
        newUser.setUserIsActive(false);
        userMapper.insert(newUser);

        Map<String, String> sendMap = sendConfirmationEmail(newUser);

        if ("发送频率太高，请等待五分钟".equals(sendMap.get("send_email_error_message"))) {
            responseBody.put("error_message", "发送频率太高，请等待五分钟");
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(responseBody);
        } else {
            responseBody.put("error_message", "注册成功，请前往邮箱激活账号");
            return ResponseEntity.ok(responseBody);
        }

    }

    @Override
    public ResponseEntity<?> confirmUserAccount(String userConfirmationToken) {
        Map<String, Object> responseBody = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_confirmation_token", userConfirmationToken);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            responseBody.put("error_message", "无效的确认令牌");
            logger.error("无效的用户确认令牌");
            return ResponseEntity.badRequest().body(responseBody);
        }
        if (user.isUserIsActive()) {
            responseBody.put("error_message", "账户已激活");
            logger.info("账户已激活");
            return ResponseEntity.badRequest().body(responseBody);
        }
        user.setUserIsActive(true);
        userMapper.updateById(user);
        responseBody.put("message", "账户激活成功");
        return ResponseEntity.ok(responseBody);
    }

    private boolean isInvalidField(Map<String, Object> errorMap, String fieldName, String fieldValue, int maxLength) {
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

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getUserEmail());
            mailMessage.setFrom("Jin0714ForProject@163.com");
            mailMessage.setSubject("看看我！你需要激活！");
            mailMessage.setText("想激活你的账号？点击这里 : " + "http://localhost:714/user/account/confirm?token=" + user.getUserConfirmationToken() + " !!!!!");
            javaMailSender.send(mailMessage);
            map.put("send_email_error_message", "发送成功");
        } catch (Exception e) {
            map.put("send_email_error_message", "发送失败");
            logger.error("发送邮件失败", e);
        }
        return map;
    }

}
