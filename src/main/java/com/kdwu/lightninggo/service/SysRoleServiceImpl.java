package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.dao.SysRoleDao;
import com.kdwu.lightninggo.model.SysRole;
import com.kdwu.lightninggo.pages.SysRolePage;
import com.kdwu.lightninggo.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleDao roleDao;

    @Override
    public CommonResult add(SysRole role) {
        roleDao.save(role);
        return CommonResult.success("新增角色成功！");
    }

    @Override
    public CommonResult delete(Integer id) {
        roleDao.deleteById(id);
        return CommonResult.success("刪除角色成功！");
    }

    @Override
    public CommonResult update(SysRole role) {
        roleDao.saveAndFlush(role);
        return CommonResult.success("修改角色成功！");
    }

    @Override
    public List<SysRole> getAllRole() {
        return roleDao.findAll();
    }

    @Override
    public PageUtil<SysRole> pageBySysRole(SysRolePage sysRolePage) {
        Pageable pageable = PageRequest.of(sysRolePage.getPageIndex() - 1, sysRolePage.getPageSize(), Sort.Direction.ASC, "id");

        String name = sysRolePage.getName();
        String description = sysRolePage.getDescription();

        Page<SysRole> pageBySysRole;
        if (name.isEmpty() && description.isEmpty()){
            pageBySysRole = roleDao.findAll(pageable);
        }else {
            pageBySysRole = roleDao.findAllBySearchContext(name, description, pageable);
        }

        PageUtil<SysRole> pageUtil = new PageUtil();
        pageUtil.setData(pageBySysRole.getContent());
        pageUtil.setTotalCount(pageBySysRole.getTotalElements());
        return pageUtil;
    }

    @Override
    public SysRole findByPrimaryKey(Integer id) {
        return roleDao.findById(id).get();
    }
}
