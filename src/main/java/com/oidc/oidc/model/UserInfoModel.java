package com.oidc.oidc.model;

import java.util.Objects;

/**
 * @author 晋晨曦
 */
public class UserInfoModel {

    private Integer id;
    private String userName;
    private String userNickname;
    private String userEmail;
    private String userAvatar;
    private String userIntroduction;
    private Boolean userIfAdministrator;

    public UserInfoModel() {
    }

    public UserInfoModel(Integer id, String userName, String userNickname, String userEmail, String userAvatar, String userIntroduction, Boolean userIfAdministrator) {
        this.id = id;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userAvatar = userAvatar;
        this.userIntroduction = userIntroduction;
        this.userIfAdministrator = userIfAdministrator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public String getUserIntroduction() {
        return userIntroduction;
    }

    public Boolean getUserIfAdministrator() {
        return userIfAdministrator;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public void setUserIntroduction(String userIntroduction) {
        this.userIntroduction = userIntroduction;
    }

    public void setUserIfAdministrator(Boolean userIfAdministrator) {
        this.userIfAdministrator = userIfAdministrator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInfoModel that = (UserInfoModel) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(userNickname, that.userNickname) &&
                Objects.equals(userEmail, that.userEmail) &&
                Objects.equals(userAvatar, that.userAvatar) &&
                Objects.equals(userIntroduction, that.userIntroduction) &&
                Objects.equals(userIfAdministrator, that.userIfAdministrator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userNickname, userEmail, userAvatar, userIntroduction, userIfAdministrator);
    }

    @Override
    public String toString() {
        return "UserInfoModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userIntroduction='" + userIntroduction + '\'' +
                ", userIfAdministrator=" + userIfAdministrator +
                '}';
    }
}
