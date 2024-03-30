package com.oidc.oidc.service.impl.user.friend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.FriendRequestMapper;
import com.oidc.oidc.mapper.FriendsMapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.Friends;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.interfaces.user.friend.DeleteFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class DeleteFriendServiceImpl implements DeleteFriendService {
    private static final Logger logger = LoggerFactory.getLogger(DeleteFriendServiceImpl.class);

    private final FriendsMapper friendsMapper;


    public DeleteFriendServiceImpl(FriendsMapper friendsMapper) {

        this.friendsMapper = friendsMapper;
    }

    @Override
    public ResponseEntity<?> deleteFriend(Map<String, String> mapParams) {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        try {
            int friendId = Integer.parseInt(mapParams.get("friendId"));
            QueryWrapper<Friends> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("user_id", user.getId()).eq("friend_id", friendId);
            Friends friends1 = friendsMapper.selectOne(queryWrapper1);
            QueryWrapper<Friends> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("user_id", friendId).eq("friend_id", user.getId());
            Friends friends2 = friendsMapper.selectOne(queryWrapper1);
            if (friends1 == null || friends2 == null) {
                responseBody.put("error_message", "不存在好友关系");
                logger.error("不存在好友关系");
                return ResponseEntity.badRequest().body(responseBody);
            }
            friendsMapper.delete(queryWrapper1);
            friendsMapper.delete(queryWrapper2);
            responseBody.put("success_message", "删除好友成功");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error_message", "参数错误");
            logger.error("参数错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
