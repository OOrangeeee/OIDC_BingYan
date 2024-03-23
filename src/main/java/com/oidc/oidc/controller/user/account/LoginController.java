package com.oidc.oidc.controller.user.account;

import com.oidc.oidc.service.interfaces.user.account.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author ChenXi Jin
 */
@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/user/account/token/")
    public Map<String, String> getUserToken(@RequestParam Map<String, String> mapParams){
        String userName=mapParams.get("userName");
        String userPassword=mapParams.get("userPassword");
        return loginService.getUserToken(userName,userPassword);
    }
}
