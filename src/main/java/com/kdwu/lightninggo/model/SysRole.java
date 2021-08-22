package com.kdwu.lightninggo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
@NoArgsConstructor
@Entity
@Table(name = "SYS_ROLE")
public class SysRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROLE_NO", unique = true, nullable = false, length = 15)
    private Integer id;

    @NonNull
    @Column(name = "ROLE_NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Column(name = "AVAILABLE", length = 1)
    private Boolean available = Boolean.FALSE;

    @OneToMany(mappedBy = "sysRole")
    private Set<SysUserRole> sysUserRoles = new HashSet<>();

    @OneToMany(mappedBy = "sysRole")
    private Set<SysRolePermission> sysRolePermissions = new HashSet<>();

    public Set<SysUserRole> getSysUserRoles() {
        return sysUserRoles;
    }

    public Set<SysRolePermission> getSysRolePermissions() {
        return sysRolePermissions;
    }
}
