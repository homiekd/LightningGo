package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.dao.SysPermissionDao;
import com.kdwu.lightninggo.model.SysPermission;
import com.kdwu.lightninggo.pages.SysPermissionPage;
import com.kdwu.lightninggo.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sysPermissionService")
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionDao permissionDao;

    @Override
    public CommonResult add(SysPermission permission) {

        // 如果插入當前節點為根節點, parentId指定為0
        if (permission.getParentId().intValue() == 0) {
            permission.setLevel(1); //根節點層級為1
            permission.setPath(null); //根節點路徑為空
        } else {
            SysPermission parentPermission = permissionDao.findById(permission.getParentId().intValue());
            if (parentPermission == null) {
                return CommonResult.badRequest("未查詢到對應的父節點！");
            }
            permission.setLevel(parentPermission.getLevel().intValue() + 1);
            if (!parentPermission.getPath().isEmpty()) {
                permission.setPath(parentPermission.getPath() + ";" + parentPermission.getId());
            } else {
                permission.setPath(parentPermission.getId().toString());
            }
        }

        permissionDao.save(permission);
        return CommonResult.success("新增權限成功！");
    }

    @Override
    public CommonResult update(SysPermission permission) {
        permissionDao.saveAndFlush(permission);
        return CommonResult.success("修改權限成功！");
    }

    @Override
    public List<SysPermission> getAllPermission() {
        return permissionDao.findAll();
    }

    @Override
    public PageUtil<SysPermission> pageByPermission(SysPermissionPage sysPermissionPage) {
        Pageable pageable = PageRequest.of(sysPermissionPage.getPageIndex() - 1, sysPermissionPage.getPageSize(), Sort.Direction.ASC, "id");

        String name = sysPermissionPage.getName();
        String code = sysPermissionPage.getCode();

        Page<SysPermission> pageBySysPermission;
        if (name.isEmpty() && code.isEmpty()){
            pageBySysPermission = permissionDao.findAll(pageable);
        }else {
            pageBySysPermission = permissionDao.findAllBySearchContext(name, code, pageable);
        }

        PageUtil<SysPermission> pageUtil = new PageUtil();
        pageUtil.setData(pageBySysPermission.getContent());
        pageUtil.setTotalCount(pageBySysPermission.getTotalElements());
        return pageUtil;
    }

    @Override
    public SysPermission findByPrimaryKey(Integer id) {
        return permissionDao.findById(id).get();
    }
}
