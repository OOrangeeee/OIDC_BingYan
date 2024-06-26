package com.oidc.oidc.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oidc.oidc.mapper.UserMapper;
import com.oidc.oidc.pojo.User;
import com.oidc.oidc.service.impl.tools.JwtTool;
import com.oidc.oidc.service.impl.tools.UserDetailImpl;
import com.oidc.oidc.service.interfaces.user.account.UserLoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;



/**
 * @author 晋晨曦
 */
@Service
public class UserLoginServiceImpl implements UserLoginService {

    // 使用@Autowired注解自动注入AuthenticationManager实例
    // AuthenticationManager是Spring Security提供的用于处理认证的接口
    private final AuthenticationManager authenticationManager;

    private static final Logger logger = LoggerFactory.getLogger(UserLoginServiceImpl.class);

    private final UserMapper userMapper;

    public UserLoginServiceImpl(AuthenticationManager authenticationManager, UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseEntity<?> getUserToken(String userName, String userPassword) {
        // 创建一个Map用于返回响应信息
        Map<String, Object> responseBody = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",userName);
        User checkUser=userMapper.selectOne(queryWrapper);

        if(!checkUser.isUserIsActive())
        {
            responseBody.put("error_message","用户未激活");
            return ResponseEntity.badRequest().body(responseBody);
        }

        // 创建UsernamePasswordAuthenticationToken对象，它是Spring Security用于封装用户名和密码的令牌
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, userPassword);

        // 使用authenticationManager的authenticate方法进行认证
        // 如果认证失败，Spring Security会抛出异常，所以这里不需要额外处理认证失败的情况
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // 认证通过后，可以从返回的Authentication对象中获取认证信息
        // 这里将其强制转换为自定义的UserDetailImpl类的实例，以便获取更多用户信息
        UserDetailImpl loginUser = (UserDetailImpl) authenticate.getPrincipal();

        // 通过loginUser对象获取用户信息
        User user = loginUser.getUser();

        // 使用用户的id生成JWT令牌
        String jwt = JwtTool.createJwt(user.getId().toString());


        // 添加登录成功的消息
        responseBody.put("message", "登录成功");
        // 将生成的JWT令牌放入Map中返回
        responseBody.put("token", jwt);

        return ResponseEntity.ok(responseBody);
    }
}

