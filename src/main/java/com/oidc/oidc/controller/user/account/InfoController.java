package com.oidc.oidc.controller.user.account;

import com.oidc.oidc.service.interfaces.user.account.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
