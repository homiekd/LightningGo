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
    private String name;

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

    @ManyToMany(cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    private Set<SysRole> roles;

    public Set<SysRole> getRoles() {
        return roles;
    }
    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    //實作權限登入

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}



