package com.kdwu.lightninggo.controller;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.SysPermission;
import com.kdwu.lightninggo.pages.SysPermissionPage;
import com.kdwu.lightninggo.service.SysPermissionService;
import com.kdwu.lightninggo.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("SysPermission")
@Api(tags = "系統權限控制器")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @ApiOperation(value = "權限表分頁")
    @PostMapping("/FindPage")
    public PageUtil<SysPermission> findPage(@RequestBody SysPermissionPage sysPermissionPage) {
        PageUtil<SysPermission> pages = sysPermissionService.pageByPermission(sysPermissionPage);
        return pages;
    }

    @ApiOperation(value = "新增權限")
    @PostMapping("/Add")
    public CommonResult add(@Valid @RequestBody SysPermission sysPermission, BindingResult bindingResult) {

        // 資料驗證
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.forEach(fieldError -> {
                log.error("error field is : {} ,message is : {}", fieldError.getField(), fieldError.getDefaultMessage());
            });

            return CommonResult.badRequest(fieldErrors);
        }

        return sysPermissionService.add(sysPermission);
    }

    @ApiOperation(value = "修改權限")
    @PutMapping("/Update")
    public CommonResult update(@RequestBody SysPermission sysPermission){
        return sysPermissionService.update(sysPermission);
    }

    @ApiOperation(value = "依id取得單筆權限")
    @GetMapping(value = "/Get/{pid}")
    public CommonResult getPermission(@PathVariable("pid") Integer pid){
        SysPermission byPrimaryKey = sysPermissionService.findByPrimaryKey(pid);
        return CommonResult.success(byPrimaryKey, "取得權限成功！");
    }

}
