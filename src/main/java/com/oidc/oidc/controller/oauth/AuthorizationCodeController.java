package com.oidc.oidc.controller.oauth;

import com.oidc.oidc.service.interfaces.oauth.AuthorizationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class AuthorizationCodeController {

    final
    AuthorizationCodeService authorizationCodeService;

    public AuthorizationCodeController(AuthorizationCodeService authorizationCodeService) {
        this.authorizationCodeService = authorizationCodeService;
    }

    @GetMapping("/oauth/getAuthorizationCode")
    public ResponseEntity<?> getAuthorizationCode(@RequestParam Map<String,String> mapParams){
        if(!"true".equals(mapParams.get("ifAuthorization")))
        {
            Map<String,Object> responseBody=new HashMap<>();
            responseBody.put("error_message","用户选择不授权");
            return ResponseEntity.badRequest().body(responseBody);
        }
        return authorizationCodeService.createAuthorizationCode(mapParams);
    }
}
