package com.kdwu.lightninggo.dao;

import com.kdwu.lightninggo.model.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserDao extends JpaRepository<SysUser, Integer> {
    public SysUser getByUsername(String username);
}
