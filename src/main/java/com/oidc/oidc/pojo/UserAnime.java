package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Objects;

/**
 * @author 晋晨曦
 */
@TableName("user_anime_info")
public class UserAnime {
    @TableField("id")
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("anime_id")
    private Integer animeId;
    @TableField("user_anime_status")
    private Integer userAnimeStatus;
    @TableField("user_anime_score")
    private Integer userAnimeScore;
    @TableField("user_anime_comment")
    private String userAnimeComment;

    // 无参构造函数
    public UserAnime() {
    }

    // 有参构造函数
    public UserAnime(Integer id, Integer userId, Integer animeId, Integer userAnimeStatus, Integer userAnimeScore, String userAnimeComment) {
        this.id = id;
        this.userId = userId;
        this.animeId = animeId;
        this.userAnimeStatus = userAnimeStatus;
        this.userAnimeScore = userAnimeScore;
        this.userAnimeComment = userAnimeComment;
    }

    // Getter and Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAnimeId() {
        return animeId;
    }

    public void setAnimeId(Integer animeId) {
        this.animeId = animeId;
    }

    public Integer getUserAnimeStatus() {
        return userAnimeStatus;
    }

    public void setUserAnimeStatus(Integer userAnimeStatus) {
        this.userAnimeStatus = userAnimeStatus;
    }

    public Integer getUserAnimeScore() {
        return userAnimeScore;
    }

    public void setUserAnimeScore(Integer userAnimeScore) {
        this.userAnimeScore = userAnimeScore;
    }

    public String getUserAnimeComment() {
        return userAnimeComment;
    }

    public void setUserAnimeComment(String userAnimeComment) {
        this.userAnimeComment = userAnimeComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAnime)) {
            return false;
        }
        UserAnime that = (UserAnime) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(animeId, that.animeId) &&
                Objects.equals(userAnimeStatus, that.userAnimeStatus) &&
                Objects.equals(userAnimeScore, that.userAnimeScore) &&
                Objects.equals(userAnimeComment, that.userAnimeComment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, animeId, userAnimeStatus, userAnimeScore, userAnimeComment);
    }

    @Override
    public String toString() {
        return "UserAnimeInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", animeId=" + animeId +
                ", userAnimeStatus=" + userAnimeStatus +
                ", userAnimeScore=" + userAnimeScore +
                ", userAnimeComment='" + userAnimeComment + '\'' +
                '}';
    }
}
