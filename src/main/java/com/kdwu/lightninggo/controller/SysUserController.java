package com.kdwu.lightninggo.controller;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.SysUser;
import com.kdwu.lightninggo.pages.SysUserPage;
import com.kdwu.lightninggo.security.SecurityUtil;
import com.kdwu.lightninggo.service.SysUserService;
import com.kdwu.lightninggo.utils.PageUtil;
import com.kdwu.lightninggo.utils.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("SysUser")
@Api(value = "後台使用者控制器")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "後台使用者登入")
    @PostMapping("/Login")
    public CommonResult login(@RequestBody SysUser sysUser) {
        CommonResult result = sysUserService.login(sysUser);
        return result;
    }

    @ApiOperation(value = "後台使用者登出")
    @GetMapping("/Logout")
    public CommonResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(null);
            redisUtil.deleteKey("userInfo_" + authentication.getName());
        }
        return CommonResult.success("成功，後台使用者已登出");
    }

    @ApiOperation(value = "取得後台使用者基本訊息")
    @GetMapping("/GetInfo")
    public CommonResult getUserInfo() {
        return CommonResult.success(SecurityUtil.getSysUser(), "成功，取得後台使用者訊息。");
    }


    @ApiOperation(value = "使用者表分頁")
    @PostMapping("/FindPage")
    public PageUtil<SysUser> findPage(@RequestBody SysUserPage sysUserPage) {
        return sysUserService.pageBySysUser(sysUserPage);
    }

    @ApiOperation(value = "新增使用者")
    @PostMapping("/Add")
    public CommonResult add(@RequestBody SysUser sysUser) {
        return sysUserService.add(sysUser);
    }

    @ApiOperation(value = "修改使用者資訊")
    @PutMapping("/Update")
    public CommonResult update(@RequestBody SysUser sysUser) {
        return sysUserService.update(sysUser);
    }

    @ApiOperation(value = "刪除使用者")
    @DeleteMapping("/Delete/{uid}")
    public CommonResult delete(@PathVariable("uid") Integer uid) {
        return sysUserService.delete(uid);
    }

    @ApiOperation(value = "取使用者所擁有的角色")
    @GetMapping("/GetUserRoles/{uid}")
    public CommonResult getUserRoles(@PathVariable("uid") Integer uid) {
        return sysUserService.getUserRolesName(uid);
    }

    @ApiOperation(value = "依id取得單筆使用者")
    @GetMapping(value = "/Get/{uid}")
    public CommonResult getUser(@PathVariable("uid") Integer uid){
         SysUser byPrimaryKey = sysUserService.findByPrimaryKey(uid);
         return CommonResult.success(byPrimaryKey, "取得使用者成功！");
    }
}
