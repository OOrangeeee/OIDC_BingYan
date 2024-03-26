package com.oidc.oidc.controller.oauth;

import com.oidc.oidc.service.interfaces.oauth.GetInfoByTokenService;
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

    public GetInfoByTokenController(GetInfoByTokenService getInfoByTokenService) {
        this.getInfoByTokenService = getInfoByTokenService;
    }

    @GetMapping("/oauth/getInfoByToken/")
    public ResponseEntity<?> getInfoByToken(HttpServletRequest request) throws Exception {
        String authorizationAccess = request.getHeader("AuthorizationAccess");
        String authorizationRefresh = request.getHeader("AuthorizationRefresh");

        return getInfoByTokenService.getInfoByToken(authorizationAccess, authorizationRefresh);
    }
}
