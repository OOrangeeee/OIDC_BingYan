package com.oidc.oidc.service.impl.user.friend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.FriendsMapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.model.UserInfoModel;
import com.oidc.oidc.pojo.Friends;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.interfaces.user.friend.GetFriendsInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class GetFriendInfoServiceImpl implements GetFriendsInfoService {
    private static final Logger logger = LoggerFactory.getLogger(GetFriendInfoServiceImpl.class);

    private final FriendsMapper friendsMapper;

    private final UserMapper userMapper;


    public GetFriendInfoServiceImpl(FriendsMapper friendsMapper, UserMapper userMapper) {

        this.userMapper = userMapper;
        this.friendsMapper = friendsMapper;
    }

    @Override
    public ResponseEntity<?> getFriendsInfo() {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        try {
            QueryWrapper<Friends> queryWrapperFriends = new QueryWrapper<>();
            queryWrapperFriends.eq("user_id", user.getId());
            List<Friends> friendsList = friendsMapper.selectList(queryWrapperFriends);
            List<UserInfoModel> friendsInfo = new ArrayList<>();
            for (Friends friends : friendsList) {
                User friend = userMapper.selectById(friends.getFriendId());
                UserInfoModel userInfoModel = new UserInfoModel(friend.getId(), friend.getUserName(), friend.getUserNickname(), friend.getUserEmail(), friend.getUserAvatar(), friend.getUserIntroduction(), friend.isUserIfAdministrator());
                friendsInfo.add(userInfoModel);
            }
            responseBody.put("好友信息", friendsInfo);
            responseBody.put("error_message", "获取好友信息成功");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error_message", "参数错误");
            logger.error("参数错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
