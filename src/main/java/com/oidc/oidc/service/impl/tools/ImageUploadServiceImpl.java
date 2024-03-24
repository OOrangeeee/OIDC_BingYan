package com.oidc.oidc.service.impl.tools;

import com.oidc.oidc.service.interfaces.tools.ImageUploadService;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 晋晨曦
 */
@Service
public class ImageUploadServiceImpl implements ImageUploadService {

    @Value("${image.upload.email}")
    private String email;

    @Value("${image.upload.password}")
    private String password;

    private String token;

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        if (token == null || token.isEmpty()) {
            token = getToken();
        }
        return uploadImageToImgtp(image, token);
    }

    private String getToken() throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 创建一个POST请求到https://www.imgtp.com/api/token
            HttpPost post = new HttpPost("https://www.imgtp.com/api/token");
            //构建包含多个部分的请求体
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addTextBody("email", email, ContentType.TEXT_PLAIN);
            builder.addTextBody("password", password, ContentType.TEXT_PLAIN);
            // 构建HttpEntity
            HttpEntity multipart = builder.build();
            post.setEntity(multipart);
            // 执行请求并获取响应
            HttpResponse response = client.execute(post);
            String responseString = EntityUtils.toString(response.getEntity());
            // 解析响应内容获取令牌
            JSONObject jsonObject = new JSONObject(responseString);
            return jsonObject.getJSONObject("data").getString("token");
        }
    }

    private String uploadImageToImgtp(MultipartFile image, String token) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost("https://www.imgtp.com/api/upload");
            // 构建请求体，包括图片和token
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("image", image.getInputStream(), ContentType.DEFAULT_BINARY, image.getOriginalFilename());
            if (token != null && !token.isEmpty()) {
                post.setHeader("token", token);
            }
            HttpEntity multipart = builder.build();
            post.setEntity(multipart);
            HttpResponse response = client.execute(post);
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = new JSONObject(responseString);
            return jsonObject.getJSONObject("data").getString("url");
        }
    }
}
