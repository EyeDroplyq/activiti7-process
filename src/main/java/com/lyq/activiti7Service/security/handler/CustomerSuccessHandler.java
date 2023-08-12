package com.lyq.activiti7Service.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyq.activiti7Service.utils.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description: 登录成功处理器
 * @author: lyq
 * @createDate: 2023-08-03 21:28
 * @version: 1.0
 */
@Component
public class CustomerSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.ok("认证成功")));
    }
}
