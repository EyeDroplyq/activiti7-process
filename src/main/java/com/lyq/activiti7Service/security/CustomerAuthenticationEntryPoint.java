package com.lyq.activiti7Service.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyq.activiti7Service.utils.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: lyq
 * @createDate: 2023-08-07 22:03
 * @version: 1.0
 */
@Component
public class CustomerAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Resource
    private ObjectMapper objectMapper;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        String s = objectMapper.writeValueAsString(Result.build(50008, "您的身份已过期,请先登录"));
        response.getWriter().write(s);
    }
}
