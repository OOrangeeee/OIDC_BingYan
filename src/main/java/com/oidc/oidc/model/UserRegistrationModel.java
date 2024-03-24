package com.oidc.oidc.model;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 晋晨曦
 */
public class UserRegistrationModel {
    private String userName;
    private String userPassword;
    private String userConfirmPassword;
    private String userNickname;
    private String userEmail;
    private String userIntroduction;
    private MultipartFile userAvatarFile;

    public UserRegistrationModel() {
    }

    public UserRegistrationModel(String userName, String userPassword, String userConfirmPassword, String userNickname, String userEmail, String userIntroduction, MultipartFile userAvatarFile) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userConfirmPassword = userConfirmPassword;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userIntroduction = userIntroduction;
        this.userAvatarFile = userAvatarFile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserConfirmPassword() {
        return userConfirmPassword;
    }

    public void setUserConfirmPassword(String userConfirmPassword) {
        this.userConfirmPassword = userConfirmPassword;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserIntroduction() {
        return userIntroduction;
    }

    public void setUserIntroduction(String userIntroduction) {
        this.userIntroduction = userIntroduction;
    }

    public MultipartFile getUserAvatarFile() {
        return userAvatarFile;
    }

    public void setUserAvatarFile(MultipartFile userAvatarFile) {
        this.userAvatarFile = userAvatarFile;
    }
}

