package com.oidc.oidc.controller.anime.tags;

import com.oidc.oidc.service.interfaces.anime.tags.AddNewTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class AddNewTagController {
    private final AddNewTagService addNewTagService;

    public AddNewTagController(AddNewTagService addNewTagService) {

        this.addNewTagService = addNewTagService;
    }

    @PostMapping("/anime/addTag/")
    public ResponseEntity<?> addNewTag(@RequestParam Map<String, String> mapParams) {
        String newTagNames = mapParams.get("newTagNames");
        return addNewTagService.addNewTag(newTagNames);
    }
}
