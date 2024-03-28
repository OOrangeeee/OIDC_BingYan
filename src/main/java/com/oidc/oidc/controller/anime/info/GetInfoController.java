package com.oidc.oidc.controller.anime.info;

import com.oidc.oidc.service.interfaces.anime.info.GetInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class GetInfoController {
    private final GetInfoService getInfoService;

    public GetInfoController(GetInfoService getInfoService) {

        this.getInfoService = getInfoService;
    }

    @GetMapping("/anime/getinfo/")

    public ResponseEntity<?> getInfo(@RequestParam Map<String, String> mapParams) {
        String id = mapParams.get("id");
        if (id == null) {
            return ResponseEntity.badRequest().body("无动漫id");
        }
        return getInfoService.getInfoOfAnime(Integer.parseInt(id));

    }
}
