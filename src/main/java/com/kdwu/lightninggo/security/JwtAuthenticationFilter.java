package com.kdwu.lightninggo.security;

import com.kdwu.lightninggo.service.UserDetailsServiceImpl;
import com.kdwu.lightninggo.utils.JwtTokenUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token認證
 * 在介面訪問前進行過濾
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private  JwtTokenUtil tokenUtil;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private String tokenHeader = "Authorization";
    private String tokenHead = "Bearer ";

    /**
     * 請求前獲取請求訊息token
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // step1. 獲取token
        String header = httpServletRequest.getHeader(tokenHeader);

        // step2. 判斷token是否存在
        if (header != null && header.startsWith(tokenHead)){
            // 拿到token 主體
            String token = header.substring(tokenHead.length());
            // 根據token獲取用戶名
            String username = tokenUtil.getUsernameByToken(token);
            // token存在，但是尚未登錄
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // 沒有登錄訊息，直接登錄
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // 判斷token是否有效
                if (!tokenUtil.isExpiration(token) && username.equals(userDetails.getUsername())){
                    // 刷新security中的用戶訊息
                    UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }
        }

        //過濾器放行
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }
}
