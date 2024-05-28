package com.example.ssodemo.filter;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ssodemo.mappers.UserMapper;
import com.example.ssodemo.models.LoginUser;
import com.example.ssodemo.models.User;
import com.example.ssodemo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Token");
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request, response);
            return;
        }

        try
        {
            Map<String, Object> claims = JwtUtil.decodeJwt(token);
            String userName = (String) claims.get("userName");
            String redisToken = this.redisTemplate.opsForValue().get(userName);
            List<String> permission = JSON.parseObject(this.redisTemplate.opsForValue().get(userName + "_permissions"), List.class);
            User user = new User();
            user.setUserName(userName);
            LoginUser loginUser = new LoginUser(user, permission);

            if(StringUtils.hasText(redisToken) && redisToken.equals(token)){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, null, loginUser.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                filterChain.doFilter(request, response);
            }else{
                throw new RuntimeException("User does not exist.");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("token is not in right format.");
        }
    }
}
