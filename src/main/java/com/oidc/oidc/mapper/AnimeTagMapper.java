package com.oidc.oidc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oidc.oidc.pojo.AnimeTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 晋晨曦
 */
@Mapper
public interface AnimeTagMapper extends BaseMapper<AnimeTag> {
    @Select("SELECT MAX(id) FROM anime_tag")
    Integer findMaxId();
}
