package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * @author 晋晨曦
 */
@TableName("authorization_code_info")
public class AuthorizationCode {

    @TableField("id")
    private int id;

    @TableField("code_word")
    private String codeWord;

    @TableField("code_if_nick_name")
    private Boolean codeIfNickName;

    @TableField("code_if_email")
    private Boolean codeIfEmail;

    @TableField("code_if_avatar")
    private Boolean codeIfAvatar;

    @TableField("code_if_introduction")
    private Boolean codeIfIntroduction;

    @TableField("code_user_id")
    private int codeUserId;

    // 无参构造函数
    public AuthorizationCode() {
    }

    // 有参构造函数
    public AuthorizationCode(int id, String codeWord, Boolean codeIfNickName, Boolean codeIfEmail, Boolean codeIfAvatar, Boolean codeIfIntroduction, int codeUserId) {
        this.id = id;
        this.codeWord = codeWord;
        this.codeIfNickName = codeIfNickName;
        this.codeIfEmail = codeIfEmail;
        this.codeIfAvatar = codeIfAvatar;
        this.codeIfIntroduction = codeIfIntroduction;
        this.codeUserId = codeUserId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodeWord() {
        return codeWord;
    }

    public void setCodeWord(String codeWord) {
        this.codeWord = codeWord;
    }

    public Boolean isCodeIfNickName() {
        return codeIfNickName;
    }

    public void setCodeIfNickName(Boolean codeIfNickName) {
        this.codeIfNickName = codeIfNickName;
    }

    public Boolean isCodeIfEmail() {
        return codeIfEmail;
    }

    public void setCodeIfEmail(Boolean codeIfEmail) {
        this.codeIfEmail = codeIfEmail;
    }

    public Boolean isCodeIfAvatar() {
        return codeIfAvatar;
    }

    public void setCodeIfAvatar(Boolean codeIfAvatar) {
        this.codeIfAvatar = codeIfAvatar;
    }

    public Boolean isCodeIfIntroduction() {
        return codeIfIntroduction;
    }

    public void setCodeIfIntroduction(Boolean codeIfIntroduction) {
        this.codeIfIntroduction = codeIfIntroduction;
    }

    public int getCodeUserId() {
        return codeUserId;
    }

    public void setCodeUserId(int codeUserId) {
        this.codeUserId = codeUserId;
    }

    // hashCode 方法
    @Override
    public int hashCode() {
        return Objects.hash(id, codeWord, codeIfNickName, codeIfEmail, codeIfAvatar, codeIfIntroduction,codeUserId);
    }

    // equals 方法
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AuthorizationCode that = (AuthorizationCode) obj;
        return id == that.id &&
                Objects.equals(codeWord, that.codeWord) &&
                Objects.equals(codeIfNickName, that.codeIfNickName) &&
                Objects.equals(codeIfEmail, that.codeIfEmail) &&
                Objects.equals(codeIfAvatar, that.codeIfAvatar) &&
                Objects.equals(codeIfIntroduction, that.codeIfIntroduction) &&
                Objects.equals(codeUserId, that.codeUserId);
    }

    // toString 方法
    @Override
    public String toString() {
        return "AuthorizationCode{" +
                "id=" + id +
                ", codeWord='" + codeWord + '\'' +
                ", codeIfNickName=" + codeIfNickName +
                ", codeIfEmail=" + codeIfEmail +
                ", codeIfAvatar=" + codeIfAvatar +
                ", codeIfIntroduction=" + codeIfIntroduction +
                ", codeUserId=" + codeUserId +
                '}';
    }
}
