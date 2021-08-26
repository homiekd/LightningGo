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
@Table(name = "SYS_PERMISSION")
public class SysPermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PERMISSION_NO", unique = true, nullable = false, length = 15)
    private Integer id;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "RESOURCE_TYPE", length = 50)
    private String resourcetype;

    @Column(name = "URL", length = 100)
    private String url;

    @Column(name = "PERMISSION", length = 100)
    private String permission;

    @Column(name = "PARENTID", length = 30)
    private String parentId;

    @Column(name = "PARENTIDS", length = 100)
    private String parentIds;

    @Column(name = "AVAILABLE", length = 1)
    private Boolean available = Boolean.FALSE;

    @JsonIgnore
    @OneToMany(mappedBy = "sysPermission", fetch = FetchType.EAGER)
    private Set<SysRolePermission> sysRolePermissions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysPermission that = (SysPermission) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
