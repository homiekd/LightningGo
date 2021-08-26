package com.kdwu.lightninggo.controller;

import com.kdwu.lightninggo.common.CommonResult;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
@Api(value = "歡迎測試控制器")
public class HelloController {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/project")
    public String helloProject() {
        return "Hello, Lightning GO Project!!";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/testAuth")
    public CommonResult testRoleAuthorize(){
        return CommonResult.forbidden("權限不足，請通知管理員！！");
    }
}
