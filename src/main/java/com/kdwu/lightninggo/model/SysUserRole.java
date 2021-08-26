package com.kdwu.lightninggo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Setter
@IdClass(SysUserRoleId.class)
@Table(name = "SYS_USER_ROLE")
public class SysUserRole implements Serializable {

    @Id
    @Column(name = "USER_NO", insertable = false, updatable = false)
    private int uId;

    @Id
    @Column(name = "ROLE_NO", insertable = false, updatable = false)
    private int rId;

    @ManyToOne
    @JoinColumn(name = "USER_NO")
    private SysUser user;

    @ManyToOne
    @JoinColumn(name = "ROLE_NO")
    private SysRole role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUserRole userRole = (SysUserRole) o;
        return uId == userRole.uId && rId == userRole.rId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uId, rId);
    }
}
