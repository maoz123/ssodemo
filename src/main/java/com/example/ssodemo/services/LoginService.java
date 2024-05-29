package com.example.ssodemo.services;

import com.example.ssodemo.models.User;

public interface LoginService {
    public String login(User user);

    String logout(User user);
}
