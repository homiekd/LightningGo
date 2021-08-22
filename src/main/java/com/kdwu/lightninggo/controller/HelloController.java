package com.kdwu.lightninggo.controller;

import com.kdwu.lightninggo.common.CommonResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/hello")
    public String helloProject() {
        return "Hello, Lightning GO Project!!";
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @RequestMapping("/testAuth")
    public CommonResult testRoleAuthorize(){
        return CommonResult.forbidden("權限不足，請通知管理員！！");
    }

}
