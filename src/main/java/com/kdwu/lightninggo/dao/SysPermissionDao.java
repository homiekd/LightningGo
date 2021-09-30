package com.kdwu.lightninggo.dao;

import com.kdwu.lightninggo.model.SysPermission;
import com.kdwu.lightninggo.model.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysPermissionDao extends JpaRepository<SysPermission, Integer> {

    /**
     * 查詢對應父節點的權限物件
     *
     * @param parentId
     * @return
     */
    public SysPermission findById(int parentId);

    @Query(value = "SELECT p FROM SysPermission p WHERE ( p.name LIKE %:name% ) " +
            " AND ( p.code LIKE %:code% ) ")
    public Page<SysPermission> findAllBySearchContext(@Param("name") String name,
                                                      @Param("code") String code,
                                                      Pageable pageable);
}