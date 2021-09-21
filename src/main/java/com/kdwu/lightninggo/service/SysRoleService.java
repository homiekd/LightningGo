package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.SysRole;
import com.kdwu.lightninggo.pages.SysRolePage;
import com.kdwu.lightninggo.utils.PageUtil;

import java.util.List;

public interface SysRoleService {
    public CommonResult add(SysRole role);

    public CommonResult delete(Integer id);

    public CommonResult update(SysRole role);

    public List<SysRole> getAllRole();

    public PageUtil<SysRole> pageBySysRole(SysRolePage sysRolePage);

    public SysRole findByPrimaryKey(Integer id);
}
