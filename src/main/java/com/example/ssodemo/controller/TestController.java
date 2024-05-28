package com.example.ssodemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @RequestMapping("/value")
    public String getToken(String key){
        return key + " : 123";
    }
}
