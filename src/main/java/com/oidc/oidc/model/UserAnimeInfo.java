package com.oidc.oidc.model;

import java.util.Objects;

/**
 * @author 晋晨曦
 */
public class UserAnimeInfo {
    private Integer userId;
    private Integer animeId;
    private String userAnimeStatus;
    private Integer userAnimeScore;
    private String userAnimeComment;
    private String userAnimeTags;

    public UserAnimeInfo() {
    }

    public UserAnimeInfo(Integer userId, Integer animeId, String userAnimeStatus, Integer userAnimeScore, String userAnimeComment, String userAnimeTags) {
        this.userId = userId;
        this.animeId = animeId;
        this.userAnimeStatus = userAnimeStatus;
        this.userAnimeScore = userAnimeScore;
        this.userAnimeComment = userAnimeComment;
        this.userAnimeTags = userAnimeTags;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getAnimeId() {
        return animeId;
    }

    public String getUserAnimeStatus() {
        return userAnimeStatus;
    }

    public Integer getUserAnimeScore() {
        return userAnimeScore;
    }

    public String getUserAnimeComment() {
        return userAnimeComment;
    }

    public String getUserAnimeTags() {
        return userAnimeTags;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAnimeId(Integer animeId) {
        this.animeId = animeId;
    }

    public void setUserAnimeStatus(String userAnimeStatus) {
        this.userAnimeStatus = userAnimeStatus;
    }

    public void setUserAnimeScore(Integer userAnimeScore) {
        this.userAnimeScore = userAnimeScore;
    }

    public void setUserAnimeComment(String userAnimeComment) {
        this.userAnimeComment = userAnimeComment;
    }

    public void setUserAnimeTags(String userAnimeTags) {
        this.userAnimeTags = userAnimeTags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAnimeInfo that = (UserAnimeInfo) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(animeId, that.animeId) &&
                Objects.equals(userAnimeStatus, that.userAnimeStatus) &&
                Objects.equals(userAnimeScore, that.userAnimeScore) &&
                Objects.equals(userAnimeComment, that.userAnimeComment) &&
                Objects.equals(userAnimeTags, that.userAnimeTags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, animeId, userAnimeStatus, userAnimeScore, userAnimeComment, userAnimeTags);
    }

    @Override
    public String toString() {
        return "UserAnimeInfo{" +
                "userId=" + userId +
                ", animeId=" + animeId +
                ", userAnimeStatus='" + userAnimeStatus + '\'' +
                ", userAnimeScore=" + userAnimeScore +
                ", userAnimeComment='" + userAnimeComment + '\'' +
                ", userAnimeTags='" + userAnimeTags + '\'' +
                '}';
    }
}
