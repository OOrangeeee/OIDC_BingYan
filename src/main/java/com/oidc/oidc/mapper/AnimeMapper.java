package com.oidc.oidc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oidc.oidc.pojo.Anime;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 晋晨曦
 */
@Mapper
public interface AnimeMapper extends BaseMapper<Anime> {
    @Select("SELECT MAX(id) FROM anime_info")
    Integer findMaxId();

    @Select("SELECT * FROM anime_info WHERE anime_name LIKE CONCAT('%', #{animeName}, '%')")
    List<Anime> findByAnimeNameLike(String animeName);
}
