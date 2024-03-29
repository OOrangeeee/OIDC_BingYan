package com.oidc.oidc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oidc.oidc.pojo.Bangumi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.http.ResponseEntity;

/**
 * @author 晋晨曦
 */
@Mapper
public interface BangumiMapper extends BaseMapper<Bangumi> {
    @Select("SELECT MAX(id) FROM bangumi")
    Integer findMaxId();
}
