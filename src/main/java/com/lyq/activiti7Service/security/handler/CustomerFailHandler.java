package com.lyq.activiti7Service.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyq.activiti7Service.utils.Result;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 认证失败处理器
 * @author: lyq
 * @createDate: 2023-08-03 21:30
 * @version: 1.0
 */
@Component
public class CustomerFailHandler implements AuthenticationFailureHandler {
    @Resource
    private ObjectMapper objectMapper;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        Result result = Result.build(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(request));
    }
}
