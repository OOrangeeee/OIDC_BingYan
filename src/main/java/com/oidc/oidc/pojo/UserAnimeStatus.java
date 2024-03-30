package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * @author 晋晨曦
 */
@TableName("user_anime_status_info")
public class UserAnimeStatus {
    @TableField("id")
    private Integer id;
    @TableField("user_anime_status_info")
    private String userAnimeStatusInfo;

    // 无参构造函数
    public UserAnimeStatus() {
    }

    // 有参构造函数
    public UserAnimeStatus(Integer id, String userAnimeStatusInfo) {
        this.id = id;
        this.userAnimeStatusInfo = userAnimeStatusInfo;
    }

    // Getter and Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserAnimeStatusInfo() {
        return userAnimeStatusInfo;
    }

    public void setUserAnimeStatusInfo(String userAnimeStatusInfo) {
        this.userAnimeStatusInfo = userAnimeStatusInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAnimeStatus)) {
            return false;
        }
        UserAnimeStatus that = (UserAnimeStatus) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userAnimeStatusInfo, that.userAnimeStatusInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userAnimeStatusInfo);
    }

    @Override
    public String toString() {
        return "UserAnimeStatus{" +
                "id=" + id +
                ", userAnimeStatusInfo='" + userAnimeStatusInfo + '\'' +
                '}';
    }
}
