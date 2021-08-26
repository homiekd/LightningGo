package com.kdwu.lightninggo.dao;

import com.kdwu.lightninggo.model.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleDao extends JpaRepository<SysRole, Integer> {
    public SysRole findByName(String name);
}
