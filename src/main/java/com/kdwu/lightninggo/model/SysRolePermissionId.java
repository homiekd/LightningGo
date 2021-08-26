package com.kdwu.lightninggo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SysRolePermissionId implements Serializable {
    private int roleNo;
    private int permissionNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRolePermissionId that = (SysRolePermissionId) o;
        return roleNo == that.roleNo && permissionNo == that.permissionNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleNo, permissionNo);
    }
}
