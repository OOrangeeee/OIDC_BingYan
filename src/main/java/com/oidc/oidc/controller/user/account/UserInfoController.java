package com.oidc.oidc.controller.user.account;

import com.oidc.oidc.model.ImageModel;
import com.oidc.oidc.service.interfaces.tools.ImageUploadService;
import com.oidc.oidc.service.interfaces.user.account.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class UserInfoController {

    final private UserInfoService userInfoService;

    final private ImageUploadService imageUploadService;

    public UserInfoController(UserInfoService userInfoService, ImageUploadService imageUploadService) {
        this.userInfoService = userInfoService;

        this.imageUploadService = imageUploadService;
    }

    @GetMapping("/user/account/getInfo/")
    public ResponseEntity<?> getUserInfo() {
        return userInfoService.getUserInfo();
    }

    @PutMapping("/user/account/updateUserPassword/")
    public ResponseEntity<?> setUserPassword(@RequestParam Map<String, String> mapParams) {
        String userNowPassword = mapParams.get("userNowPassword");
        String userNewPassword = mapParams.get("userNewPassword");
        String userNewConfirmPassword = mapParams.get("userNewConfirmPassword");
        return userInfoService.setUserPassword(userNowPassword, userNewPassword, userNewConfirmPassword);
    }

    @PutMapping("/user/account/updateUserNickname/")
    public ResponseEntity<?> setUserNickname(@RequestParam Map<String, String> mapParams) {
        String userNewNickname = mapParams.get("userNewNickname");
        return userInfoService.setUserNickname(userNewNickname);
    }

    @PutMapping("/user/account/updateUserIntroduction/")
    public ResponseEntity<?> setUserIntroduction(@RequestParam Map<String, String> mapParams) {
        String userNewIntroduction = mapParams.get("userNewIntroduction");
        if (userNewIntroduction == null) {
            userNewIntroduction = "这个人很懒，什么都没有留下";
        }
        return userInfoService.setUserIntroduction(userNewIntroduction);
    }

    @PutMapping("/user/account/updateUserAvatar/")
    public ResponseEntity<?> setUserAvatar(@ModelAttribute ImageModel imageModel) throws IOException {
        MultipartFile userNewAvatar = imageModel.getImageFile();
        String userNewAvatarUrl = "";
        if (userNewAvatar != null && !userNewAvatar.isEmpty()) {
            userNewAvatarUrl = imageUploadService.uploadImage(userNewAvatar);
        } else {
            userNewAvatarUrl = "https://img2.imgtp.com/2024/03/23/hUReTJss.jpg";
        }
        return userInfoService.setUserAvatar(userNewAvatarUrl);
    }


}
