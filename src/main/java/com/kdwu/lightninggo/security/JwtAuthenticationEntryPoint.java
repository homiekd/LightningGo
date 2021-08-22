package com.kdwu.lightninggo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdwu.lightninggo.common.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *當用戶未登錄合token過期的情況下訪問資源
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setStatus(401);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(CommonResult.unauthorized("您尚未登錄，請先登錄後操作")));
        writer.flush();
        writer.close();
    }
}
