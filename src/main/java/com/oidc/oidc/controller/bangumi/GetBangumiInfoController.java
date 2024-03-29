package com.oidc.oidc.controller.bangumi;

import com.oidc.oidc.service.interfaces.bangumi.GetBangumiInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author 晋晨曦
 */
@RestController
public class GetBangumiInfoController {
    private final GetBangumiInfoService getBangumiInfoService;

    public GetBangumiInfoController(GetBangumiInfoService getBangumiInfoService) {

        this.getBangumiInfoService = getBangumiInfoService;
    }

    @PostMapping("/bangumi/getInfo/")

    public ResponseEntity<?> getInfo(@RequestParam Map<String, String> mapParams) {
        return getBangumiInfoService.getInfo(mapParams.get("bangumi_id"));
    }
}
