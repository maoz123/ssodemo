package com.example.ssodemo.services;

import com.alibaba.fastjson.JSON;
import com.example.ssodemo.mappers.MenuMapper;
import com.example.ssodemo.models.LoginUser;
import com.example.ssodemo.models.User;
import com.example.ssodemo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String login(User user){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(Objects.isNull(authentication)){
            throw new RuntimeException("Login failed");
        } 
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", loginUser.getUsername());
        String token = JwtUtil.createJwt(claims);
        this.redisTemplate.opsForValue().set(loginUser.getUsername(), token);
        this.redisTemplate.opsForValue().set(loginUser.getUsername() + "_permissions", JSON.toJSONString(loginUser.getPermission()));
        return token;
    }

    @Override
    public String logout() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser user = (LoginUser)usernamePasswordAuthenticationToken.getPrincipal();

        return "logout succeed.";
    }
}
