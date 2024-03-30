package com.oidc.oidc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oidc.oidc.pojo.Client;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 晋晨曦
 */
@Mapper
public interface ClientMapper extends BaseMapper<Client> {
    @Select("SELECT MAX(id) FROM client_info")
    Integer findMaxId();
}
