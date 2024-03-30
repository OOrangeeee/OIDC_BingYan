package com.oidc.oidc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oidc.oidc.pojo.UserAnimeStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 晋晨曦
 */
@Mapper
public interface UserAnimeStatusMapper extends BaseMapper<UserAnimeStatus> {
    @Select("SELECT MAX(id) FROM user_anime_status_info")
    Integer findMaxId();
}
