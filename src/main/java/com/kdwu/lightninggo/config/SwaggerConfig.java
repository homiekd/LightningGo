package com.kdwu.lightninggo.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * API接口文件配置類
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    /**
     * 建立接口文件檔
     * @return
     */
    @Bean
    public Docket createApi(){
       return new Docket(DocumentationType.SWAGGER_2)
               .useDefaultResponseMessages(false)
               .apiInfo(apiInfo())
               .select()
               .apis(RequestHandlerSelectors.basePackage("com.kdwu.lightninggo.controller"))
               .paths(PathSelectors.any())
               .build()
               .securitySchemes(securitySchemes())
               .securityContexts(securityContexts());
    }

    /**
     * 設置文檔訊息
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("閃電購物後台管理系統")
                .version("1.0.0")
                .contact(new Contact("Kirch Wu", "https://www.instagram.com/kdwu886/","homiekd@msn.com"))
                .description("閃電購物後台管理系統API文件")
                .build();
    }

    /**
     * 設置請求的訊息
     * @return
     */
    private List<ApiKey> securitySchemes(){
        List<ApiKey> list = new ArrayList<>();
        ApiKey key = new ApiKey("Authorization", "Authorization", "Header");
        list.add(key);
        return list;
    }

    /**
     * 配置security對swagger的權限
     * @return
     */
    public List<SecurityContext> securityContexts(){
        List<SecurityContext> list = new ArrayList<>();
        list.add(getSecurityContext());
        return list;
    }

    /**
     * 取得授權路徑
     * @return
     */
    private SecurityContext getSecurityContext(){
        return SecurityContext
                .builder()
                .securityReferences(securityReferences())
                .forPaths(PathSelectors.regex("hello/.*"))
                .build();
    }

    /**
     * 給授權swagger，可進行接口測試
     * @return
     */
    private List<SecurityReference> securityReferences(){
        List<SecurityReference> list = new ArrayList<>();
        //授權範圍：全域
        AuthorizationScope scope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = scope;
        list.add(new SecurityReference("Authorization", scopes));

        return list;
    }
}
