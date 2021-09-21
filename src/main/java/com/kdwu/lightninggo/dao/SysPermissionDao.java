package com.kdwu.lightninggo.dao;

import com.kdwu.lightninggo.model.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPermissionDao extends JpaRepository<SysPermission, Integer> {

    /**
     * 查詢對應父節點的權限物件
     * @param parentId
     * @return
     */
    public SysPermission findById(int parentId);
}