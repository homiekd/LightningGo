package com.kdwu.lightninggo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Data
@NoArgsConstructor
@Entity
@Table(name = "SYS_USER")
public class SysUser implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_NO", unique = true, nullable = false, length = 15)
    private Integer id;

    @NonNull
    @Column(name = "USER_NAME", nullable = false, length = 50)
    private String username;

    @NonNull
    @Column(name = "USER_PSW", nullable = false, length = 50)
    private String password;

    @NonNull
    @Column(name = "EMAIL", unique = true, nullable = false, length = 50)
    private String email;

    @NonNull
    @Column(name = "USER_CN_NAME", nullable = false, length = 50)
    private String cnName;

    @Column(name = "STATE", length = 1)
    private Boolean state = Boolean.TRUE;

    @OneToMany(mappedBy = "sysUser")
    private Set<SysUserRole> sysUserRoles = new HashSet<>();

    public Set<SysUserRole> getSysUserRoles() {
        return sysUserRoles;
    }

    /**
     * 權限 數據
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 用戶名
     * @return
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 帳號是否過期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    /**
     * 帳號是否被鎖定
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    /**
     * 認證是否過期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    /**
     * 帳號是否啟用
     * @return
     */
    @Override
    public boolean isEnabled() {
        return state;
    }
}



