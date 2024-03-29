package com.oidc.oidc.service.impl.user.search;

import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.model.UserInfoModel;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.interfaces.user.search.SearchUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 晋晨曦
 */
@Service
public class SearchUserServiceImpl implements SearchUserService {

    private final UserMapper userMapper;

    public SearchUserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<?> searchUser(Map<String, String> mapParams) {
        Map<String, Object> responseBody = new HashMap<>();
        String name = mapParams.get("name");
        List<User> usersName = userMapper.findByUserNameLike(name);
        List<User> userNickname = userMapper.findByUserNicknameLike(name);
        Set<User> mergedSet = new HashSet<>(usersName);
        mergedSet.addAll(userNickname);
        List<User> userList = new ArrayList<>(mergedSet);
        List<UserInfoModel> userInfoModels = new ArrayList<>();
        for (User user : userList) {
            userInfoModels.add(new UserInfoModel(user.getId(), user.getUserName(), user.getUserNickname(), user.getUserEmail(), user.getUserAvatar(),
                    user.getUserIntroduction(), user.isUserIfAdministrator()));
        }
        responseBody.put("error_message", "查询成功");
        responseBody.put("users", userInfoModels);
        return ResponseEntity.ok(responseBody);
    }
}
