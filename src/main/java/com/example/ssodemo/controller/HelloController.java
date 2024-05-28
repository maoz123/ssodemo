package com.example.ssodemo.controller;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @PostAuthorize("hasAnyAuthority('full')")
    public String sayHello(){
        return "redirect:http://localhost:8080/value?key=name";
    }
}
