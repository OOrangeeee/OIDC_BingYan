package com.oidc.oidc.controller.bangumi;

import com.oidc.oidc.service.interfaces.bangumi.AuthorizeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author 晋晨曦
 */
@RestController
public class AuthorizeController {
    private final AuthorizeService authorizeService;

    public AuthorizeController(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

    @GetMapping("/bangumi/authorize/")
    public RedirectView authorize() {
        return authorizeService.authorize();
    }
}
