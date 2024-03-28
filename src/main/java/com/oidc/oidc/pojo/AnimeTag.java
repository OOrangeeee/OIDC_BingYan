package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * @author 晋晨曦
 */
@TableName("anime_tag")
public class AnimeTag {
    @TableField("id")
    private Integer id;
    @TableField("anime_tag")
    private String animeTag;

    // 无参构造函数
    public AnimeTag() {
    }

    // 有参构造函数
    public AnimeTag(Integer id, String animeTag) {
        this.id = id;
        this.animeTag = animeTag;
    }

    // Getter and Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnimeTag() {
        return animeTag;
    }

    public void setAnimeTag(String animeTag) {
        this.animeTag = animeTag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnimeTag)) {
            return false;
        }
        AnimeTag animeTag1 = (AnimeTag) o;
        return Objects.equals(id, animeTag1.id) &&
                Objects.equals(animeTag, animeTag1.animeTag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, animeTag);
    }

    @Override
    public String toString() {
        return "AnimeTag{" +
                "id=" + id +
                ", animeTag='" + animeTag + '\'' +
                '}';
    }
}
