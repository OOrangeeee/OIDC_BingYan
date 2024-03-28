package com.oidc.oidc.controller.anime.status;

/**
 * @author 晋晨曦
 */

import com.oidc.oidc.service.interfaces.anime.status.AddNewStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AddNewStatusController {
    private final AddNewStatusService addNewStatusService;

    public AddNewStatusController(AddNewStatusService addNewStatusService) {

        this.addNewStatusService = addNewStatusService;
    }

    @PostMapping("/anime/addStatue/")
    public ResponseEntity<?> addNewStatus(@RequestParam Map<String, String> mapParams) {
        String newStatus = mapParams.get("newStatus");
        if (newStatus == null || newStatus.isEmpty()) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("error_message", "新状态不能为空");
            return ResponseEntity.badRequest().body(responseBody);
        }
        return addNewStatusService.addNewStatus(newStatus);
    }
}
