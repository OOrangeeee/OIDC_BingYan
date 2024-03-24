package com.oidc.oidc.controller.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.interfaces.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class RegisterController {
    private final RegisterService registerService;

    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/user/account/register/")
    public Map<String, String> getUserRegister(@RequestParam Map<String, String> mapParams) {
        String userName = mapParams.get("userName");
        String userPassword = mapParams.get("userPassword");
        String userConfirmPassword = mapParams.get("userConfirmPassword");
        String userNickname = mapParams.get("userNickname");
        String userEmail = mapParams.get("userEmail");
        String userAvatar = mapParams.get("userAvatar");
        String userIntroduction = mapParams.get("userIntroduction");
        if (userAvatar == null) {
            userAvatar = "https://img2.imgtp.com/2024/03/23/hUReTJss.jpg";
        }
        if (userIntroduction == null) {
            userIntroduction = "这个人很懒，什么都没有留下";
        }
        return registerService.getUserRegister(userName, userPassword, userConfirmPassword, userNickname, userEmail, userAvatar, userIntroduction);
    }

    @GetMapping("/user/account/confirm")
    public Map<String, String> confirmUserAccount(@RequestParam("token") String userConfirmationToken) {
        return registerService.confirmUserAccount(userConfirmationToken);
    }

}
