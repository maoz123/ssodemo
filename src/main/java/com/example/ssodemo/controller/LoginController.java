package com.example.ssodemo.controller;

import com.example.ssodemo.models.User;
import com.example.ssodemo.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("user/login")
    public String login(@RequestBody User user){
        return this.loginService.login(user);
    }

    @PostMapping("user/logout")
    public String logout(@RequestBody User user){
        return loginService.logout();
    }
}
