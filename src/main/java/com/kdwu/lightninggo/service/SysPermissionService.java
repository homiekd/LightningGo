package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.SysPermission;
import com.kdwu.lightninggo.utils.PageUtil;

import java.util.List;

public interface SysPermissionService {
    public CommonResult add(SysPermission permission);

    public CommonResult update(SysPermission permission);

    public List<SysPermission> getAllPermission();

    public PageUtil<SysPermission> pageByPermission(Integer page, Integer size);
}
