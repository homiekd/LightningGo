package com.kdwu.lightninggo.controller;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.SysUser;
import com.kdwu.lightninggo.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/login")
    public CommonResult login(@RequestBody SysUser sysUser){
        CommonResult result = sysUserService.login(sysUser);
        return result;
    }

    @GetMapping("/userById")
    public SysUser findSysUserById(){
        SysUser su = sysUserService.findByPrimaryKey(1);
        return su;
    }


}
