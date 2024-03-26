package com.oidc.oidc.controller.oauth;

import com.oidc.oidc.service.interfaces.oauth.VerifyClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class VerifyClientController {

    private final VerifyClientService verifyClientService;

    public VerifyClientController(VerifyClientService verifyClientService) {
        this.verifyClientService = verifyClientService;
    }

    @PostMapping("/oauth/verify_client")
    public ResponseEntity<?> verifyClient(@RequestParam Map<String, String> mapParams) {
        String clientName = mapParams.get("clientName");
        String clientPassword = mapParams.get("clientPassword");
        String clientRedirectionUrl = mapParams.get("clientRedirectionUrl");
        return verifyClientService.verifyClient(clientName, clientPassword, clientRedirectionUrl);
    }



}
