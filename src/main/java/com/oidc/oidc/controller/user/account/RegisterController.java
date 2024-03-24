package com.oidc.oidc.controller.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.model.UserRegistrationModel;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.interfaces.tools.ImageUploadService;
import com.oidc.oidc.service.interfaces.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class RegisterController {
    private final RegisterService registerService;

    private final ImageUploadService imageUploadService;

    public RegisterController(RegisterService registerService, ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
        this.registerService = registerService;
    }

    @PostMapping("/user/account/register/")
    public Map<String, String> getUserRegister(@ModelAttribute UserRegistrationModel userModel) throws IOException {
        String userName = userModel.getUserName();
        String userPassword = userModel.getUserPassword();
        String userConfirmPassword = userModel.getUserConfirmPassword();
        String userNickname = userModel.getUserNickname();
        String userEmail = userModel.getUserEmail();
        MultipartFile userAvatarFile = userModel.getUserAvatarFile();
        String userIntroduction = userModel.getUserIntroduction();
        String userAvatar;
        if (userAvatarFile != null && !userAvatarFile.isEmpty()) {
            userAvatar=imageUploadService.uploadImage(userAvatarFile);
        } else {
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
