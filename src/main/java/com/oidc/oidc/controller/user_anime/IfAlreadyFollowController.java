package com.oidc.oidc.controller.user_anime;

import com.oidc.oidc.service.interfaces.user_anime.IfAlreadyFollowService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class IfAlreadyFollowController {
    private final IfAlreadyFollowService ifAlreadyFollowService;

    public IfAlreadyFollowController(IfAlreadyFollowService ifAlreadyFollowService) {

        this.ifAlreadyFollowService = ifAlreadyFollowService;
    }

    @GetMapping("/user_anime/ifAlreadyFollow/")

    public ResponseEntity<?> ifAlreadyFollow(@RequestParam Map<String, String> mapParams) {
        return ifAlreadyFollowService.ifAlreadyFollow(mapParams);
    }
}
