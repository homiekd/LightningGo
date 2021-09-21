package com.kdwu.lightninggo.controller;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.SysRole;
import com.kdwu.lightninggo.pages.SysRolePage;
import com.kdwu.lightninggo.service.SysRoleService;
import com.kdwu.lightninggo.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("SysRole")
@Api(value = "系統角色控制器")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation(value = "角色表分頁")
    @PostMapping("/FindPage")
    public PageUtil<SysRole> findPage(@RequestBody SysRolePage sysRolePage) {
        return sysRoleService.pageBySysRole(sysRolePage);
    }

    @ApiOperation(value = "新增角色")
    @PostMapping("/Add")
    public CommonResult add(@RequestBody SysRole sysRole) {
        return sysRoleService.add(sysRole);
    }

    @ApiOperation(value = "修改角色")
    @PutMapping("/Update")
    public CommonResult update(@RequestBody SysRole sysRole) {
        return sysRoleService.update(sysRole);
    }

    @ApiOperation(value = "刪除角色")
    @DeleteMapping("/Delete/{rid}")
    public CommonResult delete(@PathVariable("rid") Integer rid) {
        return sysRoleService.delete(rid);
    }

    @ApiOperation(value = "依id取得單筆角色")
    @GetMapping(value = "/Get/{rid}")
    public CommonResult getRole(@PathVariable("rid") Integer rid){
        SysRole byPrimaryKey = sysRoleService.findByPrimaryKey(rid);
        return CommonResult.success(byPrimaryKey, "取得使用者成功！");
    }
}
