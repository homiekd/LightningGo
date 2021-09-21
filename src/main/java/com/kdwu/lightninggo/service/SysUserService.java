package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.SysUser;
import com.kdwu.lightninggo.pages.SysUserPage;
import com.kdwu.lightninggo.utils.PageUtil;

public interface SysUserService {

    public CommonResult add(SysUser sysUser);

    public CommonResult delete(Integer id);

    public CommonResult update(SysUser sysUser);

    public Iterable<SysUser> getAllUsers();

    public SysUser findByPrimaryKey(Integer id);

    public CommonResult login(SysUser user);

    public SysUser getSysUser(String username);

    public PageUtil<SysUser> pageBySysUser(SysUserPage sysUserPage);

    public CommonResult getUserRolesName(Integer uid);
}
