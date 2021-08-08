package com.kdwu.lightninggo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Slf4j
@Data
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

    @Transient
    private List<String> permissions;

    @ManyToMany
    @JoinTable(name = "SYS_ROLE_PERMISSION",
               joinColumns = {@JoinColumn(name = "PERMISSION_NO")},
               inverseJoinColumns = {@JoinColumn(name = "ROLE_NO")})
    private Set<SysRole> roles;

    public List<String> getPermissions() {
        return Arrays.asList(this.permission.trim().split("\\|"));
    }
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
