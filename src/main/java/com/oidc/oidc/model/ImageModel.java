package com.oidc.oidc.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 晋晨曦
 */
public class ImageModel {
    private MultipartFile imageFile;

    public ImageModel() {

    }

    public ImageModel(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((imageFile == null) ? 0 : imageFile.hashCode());
        return result;
    }
}

