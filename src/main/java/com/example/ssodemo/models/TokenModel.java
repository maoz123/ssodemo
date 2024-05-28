package com.example.ssodemo.models;

public class TokenModel {
    public TokenModel(String token) {
        this.token = token;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
