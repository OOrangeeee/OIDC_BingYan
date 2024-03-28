package com.oidc.oidc.service.impl.user_anime;

import com.oidc.oidc.mapper.AnimeTagMapper;
import com.oidc.oidc.mapper.UserAnimeStatusMapper;
import com.oidc.oidc.pojo.AnimeTag;
import com.oidc.oidc.pojo.UserAnimeStatus;
import com.oidc.oidc.service.interfaces.user_anime.GetTagsAndStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class GetTagsAndStatusServiceImpl implements GetTagsAndStatusService {
    private final AnimeTagMapper animeTagMapper;
    private final UserAnimeStatusMapper userAnimeStatusMapper;

    public GetTagsAndStatusServiceImpl(AnimeTagMapper animeTagMapper, UserAnimeStatusMapper userAnimeStatusMapper) {

        this.animeTagMapper = animeTagMapper;
        this.userAnimeStatusMapper = userAnimeStatusMapper;
    }

    @Override
    public ResponseEntity<?> getTagsAndStatus() {
        Map<String, Object> responseBody = new HashMap<>();
        List<String> tags = new ArrayList<>();
        List<String> status = new ArrayList<>();
        List<AnimeTag> animeTags = animeTagMapper.selectList(null);
        List<UserAnimeStatus> userAnimeStatuses = userAnimeStatusMapper.selectList(null);
        for (AnimeTag animeTag : animeTags) {

            tags.add(animeTag.getAnimeTag());
        }
        for (UserAnimeStatus userAnimeStatus : userAnimeStatuses) {

            status.add(userAnimeStatus.getUserAnimeStatusInfo());
        }
        responseBody.put("tags", tags);
        responseBody.put("status", status);
        responseBody.put("error_message", "获取成功");
        return ResponseEntity.ok(responseBody);


    }
}
