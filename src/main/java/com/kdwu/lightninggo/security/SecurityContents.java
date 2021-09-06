package com.kdwu.lightninggo.security;

/**
 * 可訪問的清單
 */
public class SecurityContents {
    public static final String[] ACCESS_LIST = {
            //後台登入接口
            "SysUser/Login",
            //swagger相關資源
            "/swagger-ui.html",
            "/doc.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/**",
            "/configuration/ui",
            "/configuration/security"
    };
}
