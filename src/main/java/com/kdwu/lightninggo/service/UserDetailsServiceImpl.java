package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 實現UserDetailsService介面，實現自定義登錄邏輯
 * 複寫loadUserByUsername方法
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. 根據用戶名，取得用戶實體
        SysUser sysUser = sysUserService.getSysUser(username);

        if (sysUser == null) {
            return null;
        }
        return sysUser;
    }
}
