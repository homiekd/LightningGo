package com.kdwu.lightninggo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SysUserRoleId implements Serializable {
    private int uId;
    private int rId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUserRoleId that = (SysUserRoleId) o;
        return uId == that.uId && rId == that.rId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, rId);
    }
}
