package com.oidc.oidc.service.impl.tools;

import com.oidc.oidc.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author 晋晨曦
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailImpl implements UserDetails {

    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 返回当前用户的权限
        return null;
    }

    @Override
    public String getPassword() {
        // 返回当前用户的密码
        return user.getUserPassword();
    }

    @Override
    public String getUsername() {
        // 返回当前用户的用户名
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 判断当前用户的账号是否没过期
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 判断当前用户的账号是否没被锁定
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 判断当前用户的密码是否没过期
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 判断当前用户的账号是否启用
        return true;
    }
}
