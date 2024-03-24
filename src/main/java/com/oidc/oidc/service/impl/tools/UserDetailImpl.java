package com.oidc.oidc.service.impl.tools;

import com.oidc.oidc.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 * @author 晋晨曦
 */


public class UserDetailImpl implements UserDetails {
    private User user;

    public UserDetailImpl() {
    }

    public UserDetailImpl(User user) {
        this.user = user;
    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetailImpl that = (UserDetailImpl) o;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "UserDetailImpl{" +
                "user=" + user +
                '}';
    }
}

