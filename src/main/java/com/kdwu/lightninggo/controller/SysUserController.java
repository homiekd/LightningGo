package com.kdwu.lightninggo.controller;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.SysUser;
import com.kdwu.lightninggo.security.SecurityUtil;
import com.kdwu.lightninggo.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("sys")
@Api(value = "後台使用者控制器")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "後台使用者登入")
    @PostMapping("/login")
    public CommonResult login(@RequestBody SysUser sysUser){
        CommonResult result = sysUserService.login(sysUser);
        return result;
    }

    @ApiOperation(value = "後台使用者登出")
    @GetMapping("/logout")
    public CommonResult logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return CommonResult.success("成功，後台使用者已登出");
    }

    @ApiOperation(value = "取得後台使用者基本訊息")
    @GetMapping("/getInfo")
    public CommonResult getUserInfo(){
        return CommonResult.success(SecurityUtil.getSysUser(), "成功，取得後台使用者訊息。");
    }

}
