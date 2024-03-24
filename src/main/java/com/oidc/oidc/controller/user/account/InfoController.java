package com.oidc.oidc.controller.user.account;

import com.oidc.oidc.service.interfaces.user.account.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class InfoController {

    final private InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
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


}
