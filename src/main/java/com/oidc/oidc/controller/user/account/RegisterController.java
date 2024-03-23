package com.oidc.oidc.controller.user.account;

import com.oidc.oidc.service.interfaces.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ChenXi Jin
 */
@RestController
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    private final String defaultAvatar = "https://img2.imgtp.com/2024/03/23/hUReTJss.jpg";
    private final String defaultIntroduction = "这个人很懒，什么都没有留下";

    @PostMapping("/user/account/register/")
    public Map<String, String> getUserRegister(@RequestParam Map<String, String> mapParams) {
        String username = mapParams.get("username");
        String password = mapParams.get("password");
        String confirmPassword = mapParams.get("confirmPassword");
        String nickname = mapParams.get("nickname");
        String email = mapParams.get("email");
        String avatar = mapParams.get("avatar");
        String introduction = mapParams.get("introduction");
        if (avatar == null) {
            avatar = defaultAvatar;
        }
        if (introduction == null) {
            introduction = defaultIntroduction;
        }
        return registerService.getUserRegister(username, password, confirmPassword, nickname, email, avatar, introduction);
    }
}
