package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * @author 晋晨曦
 */
@TableName("bangumi")
public class Bangumi {
    @TableField("id")
    private Integer id;
    @TableField("access_token")
    private String accessToken;
    @TableField("refresh_token")
    private String refreshToken;
    @TableField("bangumi_user_id")
    private Integer bangumiUserId;

    // 无参构造方法
    public Bangumi() {
    }

    // 全参构造方法
    public Bangumi(Integer id, String accessToken, String refreshToken, Integer bangumiUserId) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.bangumiUserId = bangumiUserId;
    }

    // Getter方法
    public Integer getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Integer getBangumiUserId() {
        return bangumiUserId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setBangumiUserId(Integer bangumiUserId) {
        this.bangumiUserId = bangumiUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bangumi bangumi = (Bangumi) o;
        return Objects.equals(id, bangumi.id) &&
                Objects.equals(accessToken, bangumi.accessToken) &&
                Objects.equals(refreshToken, bangumi.refreshToken) &&
                Objects.equals(bangumiUserId, bangumi.bangumiUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accessToken, refreshToken, bangumiUserId);
    }

    @Override
    public String toString() {
        return "Bangumi{" +
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", bangumiUserId=" + bangumiUserId +
                '}';
    }
}
