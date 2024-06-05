package com.example.ssodemo.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2LoginAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        OAuth2LoginAuthenticationToken token = (OAuth2LoginAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        if(token == null)
            filterChain.doFilter(request, response);
        else
            System.out.println(token.getAccessToken());
    }
}
