package com.example.ssodemo.authority;

import com.example.ssodemo.models.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component("ca")
public class CustomAuthority {

    public boolean hasAuthority(String authority){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser) authentication.getPrincipal();
        return user.getPermission().stream().anyMatch(perm -> perm.equals(authority));
    }
}
