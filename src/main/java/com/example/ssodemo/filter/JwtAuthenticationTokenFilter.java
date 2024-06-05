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
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
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

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

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
            //List<String> permission = JSON.parseObject(this.redisTemplate.opsForValue().get(userName + "_permissions"), List.class);
            List<String> permission = (List<String>) claims.get("permission");
            User user = new User();
            user.setUserName(userName);
            LoginUser loginUser = new LoginUser(user, permission);

            if(StringUtils.hasText(redisToken) && redisToken.equals(token)){
                //UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
                OAuth2LoginAuthenticationToken oAuth2LoginAuthenticationToken = new OAuth2LoginAuthenticationToken(clientRegistrationRepository.findByRegistrationId("github"), null);
                oAuth2LoginAuthenticationToken.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(oAuth2LoginAuthenticationToken);
                //SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
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
