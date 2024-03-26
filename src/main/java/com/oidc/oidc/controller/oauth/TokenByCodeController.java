package com.oidc.oidc.controller.oauth;

import com.oidc.oidc.service.interfaces.oauth.TokenByCodeService;
import com.oidc.oidc.service.interfaces.oauth.VerifyClientService;
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
public class TokenByCodeController {
    private final TokenByCodeService tokenByCodeService;
    private final VerifyClientService verifyClientService;

    public TokenByCodeController(TokenByCodeService tokenByCodeService, VerifyClientService verifyClientService) {

        this.tokenByCodeService = tokenByCodeService;
        this.verifyClientService = verifyClientService;
    }

    @GetMapping("/oauth/getTokenByCode/")
    public ResponseEntity<?> getTokenByCode(@RequestParam Map<String, String> mapParams) {
        String clientName = mapParams.get("clientName");
        String clientPassword = mapParams.get("clientPassword");
        String clientRedirectionUrl = mapParams.get("clientRedirectionUrl");
        @SuppressWarnings("unchecked")
        Map<String, String> clientInfo = (Map<String, String>) verifyClientService.verifyClient(clientName, clientPassword, clientRedirectionUrl).getBody();
        if (clientInfo != null && !"客户端验证通过".equals(clientInfo.get("error_message"))) {
            Map<String, String> responseBody = new HashMap<>();

            responseBody.put("error_message", clientInfo.get("error_message"));
            return ResponseEntity.badRequest().body(responseBody);
        }
        String authorizationCode = mapParams.get("authorizationCode");
        return tokenByCodeService.generateTokens(authorizationCode);
    }
}
