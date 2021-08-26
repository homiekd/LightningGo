package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.SysUser;

import java.util.List;

public interface SysUserService {

    public void add(SysUser sysUser);

    public void delete(Integer id);

    public void update(SysUser sysUser);

    public List<SysUser> getAllUsers();

    public SysUser findByPrimaryKey(Integer id);

    public CommonResult login(SysUser user);

    public SysUser getSysUser(String username);

}
