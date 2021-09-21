package com.kdwu.lightninggo.dao;

import com.kdwu.lightninggo.model.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysUserDao extends JpaRepository<SysUser, Integer> {
    public SysUser getByUsername(String username);

    @Query(value = "SELECT u FROM SysUser u WHERE ( u.username LIKE %:username% ) " +
            " AND ( u.cnName LIKE %:cnName% ) " +
            " AND ( u.email LIKE %:email% )")
    public Page<SysUser> findAllBySearchContext(@Param("username") String username,
                                                @Param("cnName") String cnName,
                                                @Param("email") String email,
                                                Pageable pageable);
}
