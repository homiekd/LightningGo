package com.kdwu.lightninggo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Slf4j
@Setter
@Getter
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"enabled", "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "authorities"})
@Table(name = "SYS_USER")
public class SysUser implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ")
    @SequenceGenerator(sequenceName = "SYS_USER_SEQ", name = "USER_SEQ", allocationSize = 1 )
    @Column(name = "USER_NO", unique = true, nullable = false, length = 15)
    private Integer id;

    @NonNull
    @Column(name = "USER_NAME", nullable = false, length = 50)
    private String username;

    @NonNull
    @Column(name = "USER_PSW", length = 100)
    private String password;

    @NonNull
    @Column(name = "EMAIL", unique = true, nullable = false, length = 50)
    private String email;

    @NonNull
    @Column(name = "USER_CN_NAME", nullable = false, length = 50)
    private String cnName;

    @Column(name = "STATE", length = 1)
    private Boolean state = Boolean.TRUE;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<SysUserRole> sysUserRoles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUser user = (SysUser) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * 根據自訂的邏輯來傳回使用者許可權。如果使用者許可權回傳空，
     * 或攔截路徑的許可權不同，則驗證不通過。
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        Set<SysUserRole> userRoles = this.getSysUserRoles();

        for (SysUserRole userRole : userRoles) {
            SysRole role = userRole.getRole();
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            }
        }

        return authorities;
    }

    /**
     * 用戶名
     * @return
     */
    @Override
    public String getUsername() {
        return this.username;
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
        return this.state;
    }
}



