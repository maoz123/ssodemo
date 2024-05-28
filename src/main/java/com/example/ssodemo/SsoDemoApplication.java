package com.example.ssodemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.ssodemo.mappers"})
@EnableGlobalAuthentication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SsoDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoDemoApplication.class, args);
    }

}
