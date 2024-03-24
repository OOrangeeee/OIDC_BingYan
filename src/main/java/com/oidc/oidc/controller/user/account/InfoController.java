package com.oidc.oidc.controller.user.account;

import com.oidc.oidc.model.ImageModel;
import com.oidc.oidc.model.UserRegistrationModel;
import com.oidc.oidc.service.interfaces.tools.ImageUploadService;
import com.oidc.oidc.service.interfaces.user.account.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class InfoController {

    final private InfoService infoService;

    final private ImageUploadService imageUploadService;

    public InfoController(InfoService infoService, ImageUploadService imageUploadService) {
        this.infoService = infoService;

        this.imageUploadService = imageUploadService;
    }

    @GetMapping("/user/account/getInfo/")
    public Map<String, String> getUserInfo() {
        return infoService.getUserInfo();
    }

    @PutMapping("/user/account/updateUserPassword/")
    public Map<String, String> setUserPassword(@RequestParam Map<String, String> mapParams) {
        String userNowPassword = mapParams.get("userNowPassword");
        String userNewPassword = mapParams.get("userNewPassword");
        String userNewConfirmPassword = mapParams.get("userNewConfirmPassword");
        return infoService.setUserPassword(userNowPassword, userNewPassword, userNewConfirmPassword);
    }

    @PutMapping("/user/account/updateUserNickname/")
    public Map<String, String> setUserNickname(@RequestParam Map<String, String> mapParams) {
        String userNewNickname = mapParams.get("userNewNickname");
        return infoService.setUserNickname(userNewNickname);
    }

    @PutMapping("/user/account/updateUserIntroduction/")
    public Map<String, String> setUserIntroduction(@RequestParam Map<String, String> mapParams) {
        String userNewIntroduction = mapParams.get("userNewIntroduction");
        if (userNewIntroduction == null) {
            userNewIntroduction = "这个人很懒，什么都没有留下";
        }
        return infoService.setUserIntroduction(userNewIntroduction);
    }

    @PutMapping("/user/account/updateUserAvatar/")
    public Map<String, String> setUserAvatar(@ModelAttribute ImageModel imageModel) throws IOException {
        MultipartFile userNewAvatar = imageModel.getImageFile();
        String userNewAvatarUrl = "";
        if (userNewAvatar != null && !userNewAvatar.isEmpty()) {
            userNewAvatarUrl = imageUploadService.uploadImage(userNewAvatar);
        } else {
            userNewAvatarUrl = "https://img2.imgtp.com/2024/03/23/hUReTJss.jpg";
        }
        return infoService.setUserAvatar(userNewAvatarUrl);
    }


}
