package com.example.ssodemo.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Permission;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class LoginUser implements UserDetails {
    private List<String> permission;

    public LoginUser(User user, List<String> permission){
        this.user = user;
        this.permission = permission;
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String > getPermission(){
        return this.permission;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permission.stream().map(x -> new SimpleGrantedAuthority(x)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
