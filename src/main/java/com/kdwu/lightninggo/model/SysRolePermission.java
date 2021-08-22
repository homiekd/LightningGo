package com.kdwu.lightninggo.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(SysRolePermissionId.class)
@Table(name = "SYS_ROLE_PERMISSION")
public class SysRolePermission implements Serializable {

    @Id
    @Column(name = "ROLE_NO", insertable = false, updatable = false)
    private int roleNo;

    @Id
    @Column(name = "PERMISSION_NO", insertable = false, updatable = false)
    private int permissionNo;

    @ManyToOne
    @JoinColumn(name = "Role_NO")
    private SysRole sysRole;

    @ManyToOne
    @JoinColumn(name = "PERMISSION_NO")
    private SysPermission sysPermission;
}
