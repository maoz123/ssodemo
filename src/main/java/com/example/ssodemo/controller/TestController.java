package com.example.ssodemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestController {

    @RequestMapping("/value")
    public String getToken(){
        return "redirect:http://localhost:8080/hello";
    }
}
