package com.oidc.oidc.controller.user.friend;

import com.oidc.oidc.service.interfaces.user.friend.GetFriendsInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class GetFriendInfoController {
    private final GetFriendsInfoService getFriendsInfoService;

    public GetFriendInfoController(GetFriendsInfoService getFriendsInfoService) {
        this.getFriendsInfoService = getFriendsInfoService;
    }

    @GetMapping("/user/friend/getFriendsInfo/")
    public ResponseEntity<?> getFriendsInfo() {
        return getFriendsInfoService.getFriendsInfo();
    }
}
