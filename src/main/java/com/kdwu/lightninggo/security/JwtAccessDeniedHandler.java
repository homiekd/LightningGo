package com.kdwu.lightninggo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kdwu.lightninggo.common.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 沒有權限時返回結果
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException ade) throws IOException, ServletException {
        httpServletResponse.setStatus(403);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(CommonResult.forbidden("權限不足，請通知管理員！")));
        writer.flush();
        writer.close();
    }
}
