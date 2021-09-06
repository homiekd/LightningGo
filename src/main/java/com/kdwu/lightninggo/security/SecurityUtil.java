package com.kdwu.lightninggo.security;

import com.kdwu.lightninggo.model.SysUser;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 取得當前登入用戶的基本訊息
 */
public class SecurityUtil {

    /**
     * 以security主體訊息中獲取後台用戶訊息
     * @return
     */
    public static SysUser getSysUser(){
        SysUser sysUser = (SysUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        sysUser.setPassword(null);
        return sysUser;
    }

    /**
     * 在security中獲取後台用戶名稱
     * @return
     */
    public static String getSysUsername(){
        SysUser user = getSysUser();
        if (user == null) {
            return null;
        }
        return user.getUsername();
    }

    /**
     * 在security中獲取後台用戶ID
     * @return
     */
    public static Integer getSysUserId(){
        SysUser user = getSysUser();
        if (user == null) {
            return null;
        }
        return user.getId();
    }
}
