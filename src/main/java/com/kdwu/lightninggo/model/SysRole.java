package com.kdwu.lightninggo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "SYS_ROLE")
public class SysRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ")
    @SequenceGenerator(sequenceName = "SYS_ROLE_SEQ", name = "ROLE_SEQ", allocationSize = 1 )
    @Column(name = "ROLE_NO", unique = true, nullable = false, length = 15)
    private Integer id;

    @NonNull
    @Column(name = "ROLE_NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    @Column(name = "AVAILABLE", length = 1)
    private Boolean available = Boolean.FALSE;

    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private Set<SysUserRole> sysUserRoles = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sRole", fetch = FetchType.EAGER)
    private Set<SysRolePermission> sysRolePermissions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRole role = (SysRole) o;
        return id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
