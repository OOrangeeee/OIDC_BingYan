package com.oidc.oidc.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Objects;

/**
 * @author 晋晨曦
 */
@TableName("anime_info")
public class Anime {
    @TableField("id")
    private Integer id;
    @TableField("anime_name")
    private String animeName;
    @TableField("anime_nums")
    private Integer animeNums;
    @TableField("anime_director")
    private String animeDirector;
    @TableField("anime_introduction")
    private String animeIntroduction;
    @TableField("anime_URL")
    private String animeUrl;

    // 无参构造函数
    public Anime() {
    }

    // 有参构造函数
    public Anime(Integer id, String animeName, Integer animeNums, String animeDirector, String animeIntroduction, String animeUrl) {
        this.id = id;
        this.animeName = animeName;
        this.animeNums = animeNums;
        this.animeDirector = animeDirector;
        this.animeIntroduction = animeIntroduction;
        this.animeUrl = animeUrl;
    }

    // Getter and Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }

    public Integer getAnimeNums() {
        return animeNums;
    }

    public void setAnimeNums(Integer animeNums) {
        this.animeNums = animeNums;
    }

    public String getAnimeDirector() {
        return animeDirector;
    }

    public void setAnimeDirector(String animeDirector) {
        this.animeDirector = animeDirector;
    }

    public String getAnimeIntroduction() {
        return animeIntroduction;
    }

    public void setAnimeIntroduction(String animeIntroduction) {
        this.animeIntroduction = animeIntroduction;
    }

    public String getAnimeUrl() {
        return animeUrl;
    }

    public void setAnimeUrl(String animeUrl) {
        this.animeUrl = animeUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Anime)) {
            return false;
        }
        Anime anime = (Anime) o;
        return Objects.equals(id, anime.id) &&
                Objects.equals(animeName, anime.animeName) &&
                Objects.equals(animeNums, anime.animeNums) &&
                Objects.equals(animeDirector, anime.animeDirector) &&
                Objects.equals(animeIntroduction, anime.animeIntroduction) &&
                Objects.equals(animeUrl, anime.animeUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, animeName, animeNums, animeDirector, animeIntroduction, animeUrl);
    }

    @Override
    public String toString() {
        return "Anime{" +
                "id=" + id +
                ", animeName='" + animeName + '\'' +
                ", animeNums=" + animeNums +
                ", animeDirector='" + animeDirector + '\'' +
                ", animeIntroduction='" + animeIntroduction + '\'' +
                ", animeUrl='" + animeUrl + '\'' +
                '}';
    }
}
