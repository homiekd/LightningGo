package com.kdwu.lightninggo.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
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
    private SysUser sysUser;

    @ManyToOne
    @JoinColumn(name = "Role_NO")
    private SysRole sysRole;

}
