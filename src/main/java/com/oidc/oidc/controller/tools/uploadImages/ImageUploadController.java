package com.oidc.oidc.controller.tools.uploadImages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 晋晨曦
 */
public class ImageUploadController {

    @Value("${image.host.email}")
    private String email;

    @Value("${image.host.password}")
    private String password;

    //访问REST服务的客户端模板工具
    private final RestTemplate restTemplate;

    public ImageUploadController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile imageFile) throws Exception {
        String token = getToken();
        if (token == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("获取TOKEN失败");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("token", token);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", imageFile.getResource());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://www.imgtp.com/api/upload", requestEntity, String.class);

        return ResponseEntity.ok(response.getBody());
    }

    private String getToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("email", email);
        map.add("password", password);
        // 如果需要刷新Token，可以添加refresh参数
        // map.add("refresh", "1");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("https://www.imgtp.com/api/token", request, String.class);

        // 解析响应体以获取Token
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                String responseBody = response.getBody();
                // 使用Jackson或Gson解析JSON获取token
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);
                if (rootNode.has("data") && rootNode.get("data").has("token")) {
                    return rootNode.get("data").get("token").asText();
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        // 如果无法获取Token或解析失败，则返回null
        return null;
    }

}
