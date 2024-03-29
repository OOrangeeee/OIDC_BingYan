package com.oidc.oidc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oidc.oidc.pojo.FriendRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 晋晨曦
 */
@Mapper
public interface FriendRequestMapper extends BaseMapper<FriendRequest> {
    @Select("SELECT MAX(id) FROM friend_request")
    Integer findMaxId();
}
