package com.oidc.oidc.service.interfaces.tools;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 晋晨曦
 */
public interface ImageUploadService {
    String uploadImage(MultipartFile image) throws IOException;

}
