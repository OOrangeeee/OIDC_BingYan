package com.oidc.oidc.controller.user.friend;

import com.oidc.oidc.service.interfaces.user.friend.DeleteFriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class DeleteFriendController {
    private final DeleteFriendService deleteFriendService;

    public DeleteFriendController(DeleteFriendService deleteFriendService) {
        this.deleteFriendService = deleteFriendService;
    }

    @DeleteMapping("/user/friend/delete/")
    public ResponseEntity<?> deleteFriend(@RequestParam Map<String, String> mapParams) {
        return deleteFriendService.deleteFriend(mapParams);
    }
}
