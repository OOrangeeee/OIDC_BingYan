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
    public Integer id;
    @TableField("access_token")
    public String accessToken;
    @TableField("refresh_token")
    public String refreshToken;

    public Bangumi() {
    }

    public Bangumi(Integer id, String accessToken, String refreshToken) {
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Integer getId() {
        return id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
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
                Objects.equals(refreshToken, bangumi.refreshToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accessToken, refreshToken);
    }

    @Override
    public String toString() {
        return "Bangumi{" +
                "id=" + id +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
