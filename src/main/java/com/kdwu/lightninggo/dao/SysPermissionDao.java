package com.kdwu.lightninggo.dao;

import com.kdwu.lightninggo.model.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPermissionDao extends JpaRepository<SysPermission, Integer> {
    public SysPermission findById(int id);
}
