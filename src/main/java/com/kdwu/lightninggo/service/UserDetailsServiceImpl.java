package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.model.SysUser;
import com.kdwu.lightninggo.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 實現UserDetailsService介面，實現自定義登入邏輯
 * 複寫loadUserByUsername方法
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser;

        if (redisUtil.hasKey("userInfo_" + username)) {
            //緩存中存在用戶訊息，直接從redis中取
            log.info(username + "，緩存中存在用戶訊息，直接從redis中取");
            sysUser = (SysUser) redisUtil.getValue("userInfo_" + username);
            redisUtil.expire("userInfo_" + username, 5);
        } else {
            // 1. 根據用戶名，取得用戶實體
            sysUser = sysUserService.getSysUser(username);
            if (sysUser == null) {
                return null;
            }

            redisUtil.setValueTime("userInfo_" + username, sysUser, 5);
        }

        return sysUser;
    }


}
