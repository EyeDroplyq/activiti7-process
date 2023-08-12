package com.lyq.activiti7Service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;

/**
 * @description: security配置
 * @author: lyq
 * @createDate: 2023-08-03 22:21
 * @version: 1.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private AuthenticationSuccessHandler customerSuccessHandler;
    @Resource
    private AuthenticationFailureHandler customerFailHandler;
    @Resource
    private LogoutSuccessHandler customerLogoutHandler;
    @Resource
    private UserDetailsService customerUserDetailService;
    @Resource
    private AuthenticationEntryPoint customerAuthenticationEntryPoint;

    //配置密码加密器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //配置service
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerUserDetailService);
    }

    //资源认证配置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginProcessingUrl("/user/login")
                .successHandler(customerSuccessHandler)
                .failureHandler(customerFailHandler)
                .and()
                .logout()
                .logoutUrl("/workflow/user/logout")
                .logoutSuccessHandler(customerLogoutHandler)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(customerAuthenticationEntryPoint);
    }
}
