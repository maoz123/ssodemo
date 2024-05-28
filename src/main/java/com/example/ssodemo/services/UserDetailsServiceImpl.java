package com.example.ssodemo.services;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.example.ssodemo.mappers.UserMapper;
import com.example.ssodemo.models.LoginUser;
import com.example.ssodemo.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper();
        User user = this.userMapper.selectOne(wrapper);
        if(user == null){
            throw  new RuntimeException("user name does not exits.");
        }
        List<String> permission = new ArrayList<>(Arrays.asList("full", "custom"));
        return new LoginUser(user, permission);
    }
}
