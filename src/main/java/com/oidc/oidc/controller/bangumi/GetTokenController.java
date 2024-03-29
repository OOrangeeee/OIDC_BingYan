package com.oidc.oidc.controller.bangumi;

import com.oidc.oidc.service.interfaces.bangumi.GetTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class GetTokenController {

    private final GetTokenService getTokenService;


    public GetTokenController(GetTokenService getTokenService) {

        this.getTokenService = getTokenService;
    }

    @GetMapping("/bangumi/getToken/")
    public ResponseEntity<?> getToken(@RequestParam Map<String, String> mapParams) {
        return getTokenService.getToken(mapParams.get("code"), mapParams.get("state"));
    }
}
