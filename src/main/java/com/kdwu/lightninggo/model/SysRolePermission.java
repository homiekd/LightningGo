package com.kdwu.lightninggo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Setter
@Getter
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
    @JoinColumn(name = "ROLE_NO")
    private SysRole sRole;

    @ManyToOne
    @JoinColumn(name = "PERMISSION_NO")
    private SysPermission sysPermission;

    public SysRolePermission (int roleNo, int permissionNo){
        this.roleNo = roleNo;
        this.permissionNo = permissionNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRolePermission that = (SysRolePermission) o;
        return roleNo == that.roleNo && permissionNo == that.permissionNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleNo, permissionNo);
    }
}
