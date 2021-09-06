package com.kdwu.lightninggo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 處理跨域訪問
 */
@Configuration
public class CrosConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //映射路徑
        registry.addMapping("/**")
                // 允許跨網域請求的來源
                .allowedOrigins("http://localhost:8888")
                // 允許跨域訪問的方法
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE")
                // 允許跨域攜帶cookie資訊，預設跨網域請求是不攜帶cookie資訊的。
                .allowCredentials(true)
                //允許哪些Header
                .allowedHeaders("*")
                //可獲取哪些Header（因為跨網域預設不能取得全部Header資訊）
//                .exposedHeaders("Header1", "Header2")
                //
                .allowedOriginPatterns("*")
                // 最大反應時間
                .maxAge(3600);
    }
}
