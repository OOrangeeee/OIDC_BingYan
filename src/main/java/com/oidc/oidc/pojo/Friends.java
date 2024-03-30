package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author 晋晨曦
 */
@TableName("friends_info")
public class Friends {
    @TableField("id")
    private Integer id;
    @TableField("user_id")
    private Integer userId;
    @TableField("friend_id")
    private Integer friendId;

    public Friends() {
    }

    public Friends(Integer id, Integer userId, Integer friendId) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Friends friends = (Friends) o;
        return Objects.equals(id, friends.id) &&
                Objects.equals(userId, friends.userId) &&
                Objects.equals(friendId, friends.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, friendId);
    }

    @Override
    public String toString() {
        return "Friends{" +
                "id=" + id +
                ", userId=" + userId +
                ", friendId=" + friendId +
                '}';
    }
}
