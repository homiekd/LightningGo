package com.kdwu.lightninggo.dao;

import com.kdwu.lightninggo.model.SysRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SysRoleDao extends JpaRepository<SysRole, Integer> {

    @Query(value = "SELECT r FROM SysRole r WHERE ( r.name LIKE %:name% ) " +
            " AND ( r.description LIKE %:description% ) ")
    public Page<SysRole> findAllBySearchContext(@Param("name") String name,
                                                @Param("description") String description,
                                                Pageable pageable);
}
