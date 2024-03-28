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
    private String userAnimeStatus;
    @TableField("user_anime_score")
    private Integer userAnimeScore;
    @TableField("user_anime_comment")
    private String userAnimeComment;
    @TableField("user_anime_tags")
    private String userAnimeTags;

    public UserAnime() {}

    // All-args constructor
    public UserAnime(Integer id, Integer userId, Integer animeId, String userAnimeStatus, Integer userAnimeScore, String userAnimeComment, String userAnimeTags) {
        this.id = id;
        this.userId = userId;
        this.animeId = animeId;
        this.userAnimeStatus = userAnimeStatus;
        this.userAnimeScore = userAnimeScore;
        this.userAnimeComment = userAnimeComment;
        this.userAnimeTags = userAnimeTags;
    }

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

    public String getUserAnimeStatus() {
        return userAnimeStatus;
    }

    public void setUserAnimeStatus(String userAnimeStatus) {
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

    public String getUserAnimeTags() {
        return userAnimeTags;
    }

    public void setUserAnimeTags(String userAnimeTags) {
        this.userAnimeTags = userAnimeTags;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, userId, animeId, userAnimeStatus, userAnimeScore, userAnimeComment, userAnimeTags);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserAnime other = (UserAnime) obj;
        return Objects.equals(id, other.id) &&
                Objects.equals(userId, other.userId) &&
                Objects.equals(animeId, other.animeId) &&
                Objects.equals(userAnimeStatus, other.userAnimeStatus) &&
                Objects.equals(userAnimeScore, other.userAnimeScore) &&
                Objects.equals(userAnimeComment, other.userAnimeComment) &&
                Objects.equals(userAnimeTags, other.userAnimeTags);
    }
    @Override
    public String toString() {
        return "UserAnime{" +
                "id=" + id +
                ", userId=" + userId +
                ", animeId=" + animeId +
                ", userAnimeStatus='" + userAnimeStatus + '\'' +
                ", userAnimeScore=" + userAnimeScore +
                ", userAnimeComment='" + userAnimeComment + '\'' +
                ", userAnimeTags='" + userAnimeTags + '\'' +
                '}';
    }
}

