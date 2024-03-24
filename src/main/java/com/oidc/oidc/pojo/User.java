package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

/**
 * @author 晋晨曦
 */

@TableName("user")
public class User {
    @TableField("id")
    private Integer id;
    @TableField("user_name")
    private String userName;
    @TableField("user_password")
    private String userPassword;
    @TableField("user_nickname")
    private String userNickname;
    @TableField("user_email")
    private String userEmail;
    @TableField("user_avatar")
    private String userAvatar;
    @TableField("user_introduction")
    private String userIntroduction;
    @TableField("user_is_active")
    private boolean userIsActive;
    @TableField("user_confirmation_token")
    private String userConfirmationToken;

    @TableField("user_last_email_sent_time")
    private Date userLastEmailSentTime;

    // 无参构造函数
    public User() {
    }

    // 有参构造函数
    public User(Integer id, String userName, String userPassword, String userNickname, String userEmail, String userAvatar, String userIntroduction, boolean userIsActive, String userConfirmationToken,Date userLastEmailSentTime) {
        this.id = id;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userAvatar = userAvatar;
        this.userIntroduction = userIntroduction;
        this.userIsActive = userIsActive;
        this.userConfirmationToken = userConfirmationToken;
        this.userLastEmailSentTime=userLastEmailSentTime;
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserIntroduction() {
        return userIntroduction;
    }

    public void setUserIntroduction(String userIntroduction) {
        this.userIntroduction = userIntroduction;
    }

    public boolean isUserIsActive() {
        return userIsActive;
    }

    public void setUserIsActive(boolean userIsActive) {
        this.userIsActive = userIsActive;
    }

    public String getUserConfirmationToken() {
        return userConfirmationToken;
    }

    public void setUserConfirmationToken(String userConfirmationToken) {
        this.userConfirmationToken = userConfirmationToken;
    }

    public Date getUserLastEmailSentTime() {
        return userLastEmailSentTime;
    }

    public void setUserLastEmailSentTime(Date lastEmailSentTime) {
        this.userLastEmailSentTime = lastEmailSentTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User user = (User) o;

        if (userIsActive != user.userIsActive) {
            return false;
        }
        if (!id.equals(user.id)) {
            return false;
        }
        if (!userName.equals(user.userName)) {
            return false;
        }
        if (!userPassword.equals(user.userPassword)) {
            return false;
        }
        if (!userNickname.equals(user.userNickname)) {
            return false;
        }
        if (!userEmail.equals(user.userEmail)) {
            return false;
        }
        if (!userAvatar.equals(user.userAvatar)) {

            return false;
        }
        if (!userIntroduction.equals(user.userIntroduction)) {
            return false;
        }
        return userConfirmationToken.equals(user.userConfirmationToken);
    }


    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userName.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userIntroduction='" + userIntroduction + '\'' +
                ", userIsActive=" + userIsActive +
                ", userConfirmationToken='" + userConfirmationToken + '\'' +
                '}';
    }
}
