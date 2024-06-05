package com.example.ssodemo.services;

import com.alibaba.fastjson.JSON;
import com.example.ssodemo.mappers.MenuMapper;
import com.example.ssodemo.models.LoginUser;
import com.example.ssodemo.models.OAuthTokenModel;
import com.example.ssodemo.models.User;
import com.example.ssodemo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationProvider;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthorizationCodeAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationProvider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${spring.security.oauth2.client.registration.github.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.github.client-secret}")
    private String clientSecret;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    public String login(User user){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(Objects.isNull(authentication)){
            throw new RuntimeException("Login failed");
        } 
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", loginUser.getUsername());
        claims.put("permission", loginUser.getPermission());
        String token = JwtUtil.createJwt(claims);
        this.redisTemplate.opsForValue().set(loginUser.getUsername(), token);
        //this.redisTemplate.opsForValue().set(loginUser.getUsername() + "_permissions", JSON.toJSONString(loginUser.getPermission()));
        return token;
    }

    @Override
    public String logout(User user) {
        String userName = user.getUserName();
        this.redisTemplate.delete(userName);
        System.out.println("delete token for user " + userName + " succeed.");

        return "logout succeed.";
    }


    public String getLoginToken(String code){
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("code", code);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> accepts = new ArrayList<>();
        accepts.add(MediaType.APPLICATION_JSON);
        headers.setAccept(accepts);
        HttpEntity<Map<String, Object>> r = new HttpEntity<>(body, headers);
        String url = "https://github.com/login/oauth/access_token";
        String result = restTemplate.postForObject(url, r, String.class);
        OAuthTokenModel tokenModel;
        try {
            tokenModel = JSON.parseObject(result, OAuthTokenModel.class);
        }catch (Exception e){
            tokenModel = null;
            throw e;
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", "testuser");
        claims.put("permission", "testpermission");
        String token = JwtUtil.createJwt(claims);
        System.out.println("exchange token succeed.");
        return token;
    }
}
