package com.example.ssodemo.controller;

import com.example.ssodemo.models.ResponseResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    //@PreAuthorize("hasAuthority('system:dept:list')")
    @PreAuthorize("@ca.hasAuthority('system:dept:list')")
    public ResponseResult sayHello(){
        return new ResponseResult(HttpStatus.OK.value(), "succeed", "hello world");
    }
}
