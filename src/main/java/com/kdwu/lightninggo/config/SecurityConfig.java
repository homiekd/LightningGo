package com.kdwu.lightninggo.config;

import com.kdwu.lightninggo.security.JwtAccessDeniedHandler;
import com.kdwu.lightninggo.security.JwtAuthenticationEntryPoint;
import com.kdwu.lightninggo.security.JwtAuthenticationFilter;
import com.kdwu.lightninggo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security Config Basic 配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private JwtAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

    /**
     * 用來配置沒有權限也可以訪問的資源
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/login");
    }

    /**
     * 自定義登錄邏輯的配置
     * 配置到Security中進行認證
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Security的核心配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // step1. 使用jwt,首先關閉csrf跨域防護
        http.csrf().disable();
        // step2. 關閉session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // step3. 請求都需要進行認證後才能訪問，除了特定資源外
        http.authorizeRequests().anyRequest().authenticated();
        // step4. 關閉緩存
        http.headers().cacheControl();
        // step5. token過濾器，校驗token
        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // step6. 沒有登錄, 沒有權限訪問時自定義返回的結果
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDeniedHandler);
    }
}
