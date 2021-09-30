package com.kdwu.lightninggo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERMISSION_SEQ")
    @SequenceGenerator(sequenceName = "SYS_PERMISSION_SEQ", name = "PERMISSION_SEQ", allocationSize = 1 )
    @Column(name = "PERMISSION_NO", unique = true, nullable = false, length = 15)
    private Integer id;

    @Column(name = "NAME", length = 50)
    private String name;

    @Column(name = "MENU_CODE", length = 50)
    private String code;

    @NotNull(message = "權限類型: 請勿空白")
    @Column(name = "RESOURCE_TYPE", nullable = false, length = 1)
    private Character resourcetype;

    @Column(name = "URL", length = 150)
    private String url;

    @Column(name = "PATH", length = 100)
    private String path;

    @Column(name = "PERMISSION", length = 100)
    private String permission;

    @Column(name = "PARENTID", length = 10)
    private Integer parentId;

    @NotNull(message = "排序欄位: 請勿空白")
    @Column(name = "SORT", nullable = false, length = 10)
    private Integer sort;

    @NotNull(message = "層級欄位: 請勿空白")
    @Column(name = "MENU_LEVEL", nullable = false, length = 10)
    private Integer level;

    @Column(name = "ICON", length = 50)
    private String icon;

    @Column(name = "AVAILABLE", length = 1)
    private Boolean available = Boolean.FALSE;

    //前端控制用
    @Column(name = "ACTIVE", length = 1)
    private Boolean active = Boolean.FALSE;

    /**
     * 子菜單集合
     */
    @Transient
    private List<SysPermission> childPermissions;

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
