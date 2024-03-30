package com.oidc.oidc.service.impl.user.friend;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.FriendRequestMapper;
import com.oidc.oidc.mapper.FriendsMapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.FriendRequest;
import com.oidc.oidc.pojo.Friends;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.impl.user.account.UserRegisterServiceImpl;
import com.oidc.oidc.service.interfaces.user.friend.AddNewFriendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 晋晨曦
 */
@Service
public class AddNewFriendServiceImpl implements AddNewFriendService {

    private static final Logger logger = LoggerFactory.getLogger(AddNewFriendServiceImpl.class);

    private final FriendsMapper friendsMapper;

    private final FriendRequestMapper friendRequestMapper;

    private final UserMapper userMapper;


    public AddNewFriendServiceImpl(FriendsMapper friendsMapper, FriendRequestMapper friendRequestMapper, UserMapper userMapper) {

        this.friendsMapper = friendsMapper;

        this.friendRequestMapper = friendRequestMapper;

        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<?> sendNewFriendRequest(Map<String, String> mapParams) {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        try {
            int toId = Integer.parseInt(mapParams.get("toId"));
            User userTo = userMapper.selectById(toId);
            if (userTo == null) {
                responseBody.put("error_message", "目标对象未找到");
                logger.error("目标对象未找到");
                return ResponseEntity.badRequest().body(responseBody);
            }
            QueryWrapper<Friends> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", user.getId()).eq("friend_id", userTo.getId());
            Friends friends = friendsMapper.selectOne(queryWrapper);
            if (friends != null) {
                responseBody.put("error_message", "已经是好友");
                logger.error("已经是好友");
                return ResponseEntity.badRequest().body(responseBody);
            }
            Integer id = friendRequestMapper.findMaxId();
            if (id == null) {
                id = 0;
            }
            id++;
            FriendRequest friendRequest = new FriendRequest(id, user.getId(), userTo.getId());
            friendRequestMapper.insert(friendRequest);
            responseBody.put("error_message", "发送成功");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error_message", "参数错误");
            logger.error("参数错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
    }

    @Override
    public ResponseEntity<?> getFriendRequestList() {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        try {
            QueryWrapper<FriendRequest> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("to_user_id", user.getId());
            List<FriendRequest> friendRequestList = friendRequestMapper.selectList(queryWrapper);
            responseBody.put("friend_request_list", friendRequestList);
            responseBody.put("error_message", "获取成功");
            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            responseBody.put("error_message", "获取失败");
            logger.error("获取失败");
            return ResponseEntity.badRequest().body(responseBody);
        }
    }

    @Override
    public ResponseEntity<?> solveFriendRequest(Map<String, String> mapParams) {
        Map<String, Object> responseBody = new HashMap<>();
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();
        try {
            Integer fromUserId = Integer.parseInt(mapParams.get("from_user_id"));
            String choice = mapParams.get("choice");
            QueryWrapper<FriendRequest> queryWrapperFriendRequest = new QueryWrapper<>();
            queryWrapperFriendRequest.eq("from_user_id", fromUserId).eq("to_user_id", user.getId());
            FriendRequest friendRequest = friendRequestMapper.selectOne(queryWrapperFriendRequest);
            if (choice == null || friendRequest == null) {
                responseBody.put("error_message", "参数错误");
                logger.error("参数错误");
                return ResponseEntity.badRequest().body(responseBody);
            }
            friendRequestMapper.delete(queryWrapperFriendRequest);
            if ("true".equals(choice)) {
                Integer id = friendsMapper.findMaxId();
                if (id == null) {
                    id = 0;
                }
                id++;
                Friends newFriends1 = new Friends(id, user.getId(), fromUserId);
                id++;
                Friends newFriends2 = new Friends(id, fromUserId, user.getId());
                friendsMapper.insert(newFriends1);
                friendsMapper.insert(newFriends2);
                responseBody.put("error_message", "添加好友成功");
                return ResponseEntity.ok(responseBody);
            } else if ("false".equals(choice)) {
                responseBody.put("error_message", "拒绝好友成功");
                return ResponseEntity.ok(responseBody);
            } else {
                responseBody.put("error_message", "参数错误");
                logger.error("参数错误");
                return ResponseEntity.badRequest().body(responseBody);
            }
        } catch (Exception e) {
            responseBody.put("error_message", "参数错误");
            logger.error("参数错误");
            return ResponseEntity.badRequest().body(responseBody);
        }
    }
}
