package com.example.ssodemo.controller;

import com.example.ssodemo.models.ResponseResult;
import com.example.ssodemo.models.TokenModel;
import com.example.ssodemo.models.User;
import com.example.ssodemo.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("user/login")
    public ResponseResult login(@RequestBody User user){
        return new ResponseResult(HttpStatus.OK.value(), "succeed", new TokenModel(this.loginService.login(user)));
    }

    @PostMapping("user/logout")
    public ResponseResult logout(@RequestBody User user){
        return new ResponseResult(HttpStatus.OK.value(), "succeed", loginService.logout(user));
    }

    @GetMapping("/login/oauth2/code/github/endpoint")
    public ResponseResult gitHubCodeEndpoint(@RequestParam String code, @RequestParam String state){
        return new ResponseResult(HttpStatus.OK.value(), "succeed", loginService.getLoginToken(code));
    }
}
