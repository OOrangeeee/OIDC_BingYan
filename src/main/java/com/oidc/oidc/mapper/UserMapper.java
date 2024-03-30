package com.oidc.oidc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oidc.oidc.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 晋晨曦
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 自定义查询最大id的方法
    @Select("SELECT MAX(id) FROM user_info")
    Integer findMaxId();

    @Select("SELECT * FROM user_info WHERE user_name LIKE CONCAT('%', #{userName}, '%')")
    List<User> findByUserNameLike(String userName);

    @Select("SELECT * FROM user_info WHERE user_nickname LIKE CONCAT('%', #{userNickname}, '%')")
    List<User> findByUserNicknameLike(String userNickname);
}
