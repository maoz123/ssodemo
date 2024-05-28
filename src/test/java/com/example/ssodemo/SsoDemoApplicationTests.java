package com.example.ssodemo;

import com.example.ssodemo.mappers.UserMapper;
import com.example.ssodemo.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
class SsoDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testUserMapper(){
        List<User> users = userMapper.selectList(null);
        Assert.isTrue(users.size() >= 1);
        System.out.println(users.get(0));
    }

    @Test
    public void testPasswordEncoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.matches("123", "$2a$10$KIfrtHXmrouWgex/QaKPdOndeDrv4Rz4IX9XZVrNsVXGm4J.oXI0."));
        System.out.println(encoder.matches("123", "$2a$10$qE333CdUd40XPjKN0pcGquzXHtBIXqJxZYSY.oORMu1Xb1u9fgRdC"));
        //$2a$10$KIfrtHXmrouWgex/QaKPdOndeDrv4Rz4IX9XZVrNsVXGm4J.oXI0.
        //$2a$10$qE333CdUd40XPjKN0pcGquzXHtBIXqJxZYSY.oORMu1Xb1u9fgRdC
//        String one = encoder.encode("123");
//        String two = encoder.encode("1234");
//        System.out.println(one);
//        System.out.println(two);
    }

}
