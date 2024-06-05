package com.example.ssodemo.config;

import com.example.ssodemo.filter.JwtAuthenticationTokenFilter;
import com.example.ssodemo.filter.JwtTokenGeneratorFilter;
import com.example.ssodemo.handler.SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private JwtTokenGeneratorFilter jwtTokenGeneratorFilter;

    @Autowired
    private SuccessHandler successHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/user/login").anonymous()
                .antMatchers("/index.html").anonymous()
                .antMatchers("/").anonymous()
                .antMatchers("/favicon.ico").anonymous()
                .antMatchers("/HelloWorld.html").anonymous()
                .antMatchers("/test").hasAuthority("test")
                .antMatchers("/login/oauth2/code/github").anonymous()
                .anyRequest().authenticated()
                .and()
                .oauth2Login().successHandler(successHandler);

        http.addFilterBefore(jwtAuthenticationTokenFilter, OAuth2AuthorizationRequestRedirectFilter.class);
        http.addFilterAfter(jwtTokenGeneratorFilter, OAuth2LoginAuthenticationFilter.class);
    }
}
