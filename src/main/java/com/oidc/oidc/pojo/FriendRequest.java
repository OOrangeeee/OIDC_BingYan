package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * @author 晋晨曦
 */
@TableName("friend_request")
public class FriendRequest {
    @TableField("id")
    private Integer id;
    @TableField("from_user_id")
    private Integer fromUserId;
    @TableField("to_user_id")
    private Integer toUserId;

    public FriendRequest() {
    }

    public FriendRequest(Integer id, Integer fromUserId, Integer toUserId) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FriendRequest that = (FriendRequest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(fromUserId, that.fromUserId) &&
                Objects.equals(toUserId, that.toUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromUserId, toUserId);
    }

    @Override
    public String toString() {
        return "FriendRequest{" +
                "id=" + id +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                '}';
    }
}
