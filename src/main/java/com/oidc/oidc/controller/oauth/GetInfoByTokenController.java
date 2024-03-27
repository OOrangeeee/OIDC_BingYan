package com.oidc.oidc.controller.oauth;

import com.oidc.oidc.service.interfaces.oauth.GetIDTokenByTokenService;
import com.oidc.oidc.service.interfaces.oauth.GetInfoByTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class GetInfoByTokenController {

    private final GetInfoByTokenService getInfoByTokenService;
    private final GetIDTokenByTokenService getIDTokenByTokenService;

    public GetInfoByTokenController(GetInfoByTokenService getInfoByTokenService, GetIDTokenByTokenService getIdTokenByTokenService) {
        this.getInfoByTokenService = getInfoByTokenService;
        this.getIDTokenByTokenService = getIdTokenByTokenService;
    }

    @GetMapping("/oauth/getInfoByToken/")
    public ResponseEntity<?> getInfoByToken(HttpServletRequest request) throws Exception {
        String authorizationAccess = request.getHeader("AuthorizationAccess");
        String authorizationRefresh = request.getHeader("AuthorizationRefresh");
        String scpoeIfOpenId = request.getHeader("scpoeIfOpenId");
        if (scpoeIfOpenId == null) {
            return getInfoByTokenService.getInfoByToken(authorizationAccess, authorizationRefresh);
        } else if ("openid".equals(scpoeIfOpenId)) {
            return getIDTokenByTokenService.getIdTokenByToken(authorizationAccess, authorizationRefresh);
        } else {
            return new ResponseEntity<>("scpoeIfOpenId参数错误", HttpStatus.BAD_REQUEST);
        }


    }
}
