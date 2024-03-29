package com.oidc.oidc.controller.user.friend;

import com.oidc.oidc.service.interfaces.user.friend.AddNewFriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class AddNewFriendController {
    private final AddNewFriendService addNewFriendService;

    public AddNewFriendController(AddNewFriendService addNewFriendService) {
        this.addNewFriendService = addNewFriendService;
    }

    @PostMapping("/user/friend/sendNewFriendRequest/")
    public ResponseEntity<?> addNewFriend(@RequestParam Map<String, String> mapParams) {
        return addNewFriendService.sendNewFriendRequest(mapParams);
    }

    @GetMapping("/user/friend/getFriendRequest/")
    public ResponseEntity<?> getFriendRequest() {
        return addNewFriendService.getFriendRequestList();
    }

    @PostMapping("/user/friend/ansFriendRequest/")
    public ResponseEntity<?> ansFriendRequest(@RequestParam Map<String, String> mapParams) {
        return addNewFriendService.solveFriendRequest(mapParams);
    }
}
