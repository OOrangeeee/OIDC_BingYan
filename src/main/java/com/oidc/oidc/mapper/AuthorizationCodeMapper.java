package com.oidc.oidc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oidc.oidc.pojo.AuthorizationCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 晋晨曦
 */
@Mapper
public interface AuthorizationCodeMapper extends BaseMapper<AuthorizationCode> {
    @Select("SELECT MAX(id) FROM authorization_code_info")
    Integer findMaxId();
}
