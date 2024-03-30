package com.oidc.oidc.controller.user.search;

import com.oidc.oidc.service.interfaces.user.search.SearchUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class SearchUserController {
    private final SearchUserService searchUserService;

    public SearchUserController(SearchUserService searchUserService) {
        this.searchUserService = searchUserService;
    }

    @GetMapping("/user/search/")
    public ResponseEntity<?> searchUser(@RequestParam Map<String, String> mapParams) {
        return searchUserService.searchUser(mapParams);
    }
}
