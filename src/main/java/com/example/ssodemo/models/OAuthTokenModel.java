package com.example.ssodemo.models;

import com.alibaba.fastjson.annotation.JSONField;

public class OAuthTokenModel {
    public String getAccessToken() {
        return accessToken;
    }

    @JSONField(name = "access_token")
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }


    public String getTokenType() {
        return tokenType;
    }

    @JSONField(name = "token_type")
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    private String accessToken;

    private String scope;

    private String tokenType;
}
