package com.example.ssodemo.controller;

import com.example.ssodemo.models.ResponseResult;
import com.example.ssodemo.models.TokenModel;
import com.example.ssodemo.models.User;
import com.example.ssodemo.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("user/login")
    public ResponseResult login(@RequestBody User user){
        return new ResponseResult(HttpStatus.OK.value(), "succeed", new TokenModel(this.loginService.login(user)));
    }

    @PostMapping("user/logout")
    public String logout(@RequestBody User user){
        return loginService.logout();
    }
}
