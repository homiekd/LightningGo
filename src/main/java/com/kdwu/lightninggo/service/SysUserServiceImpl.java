package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.dao.SysUserDao;
import com.kdwu.lightninggo.model.SysUser;
import com.kdwu.lightninggo.utils.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService{

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtTokenUtil tokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void add(SysUser sysUser) {
        sysUserDao.save(sysUser);
    }

    @Override
    public void delete(Integer id) {
        sysUserDao.deleteById(id);
    }

    @Override
    public void update(SysUser sysUser) {
        sysUserDao.saveAndFlush(sysUser);
    }

    @Override
    public SysUser findByPrimaryKey(Integer id) {
        return sysUserDao.findById(id).get();
    }

    @Override
    public List<SysUser> getAllUsers() {
        return sysUserDao.findAll() ;
    }

    @Override
    public CommonResult login(SysUser user) {
        log.info("1. 開始登入。");
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        //Step 1. 檢查用戶帳號 & 檢查用戶密碼
        if (userDetails == null){
            return CommonResult.unauthorized("登入失敗：無此用戶，請重新輸入。");
        }

        if (!passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            return CommonResult.unauthorized("登入失敗：密碼錯誤，請重新輸入。");
        }

        //Step 2. 檢查帳號是否被禁用
        if (!userDetails.isEnabled()){
            return CommonResult.forbidden("登入失敗：此帳戶已被禁用，請通知管理員。");
        }

        log.info("2. 登入成功， 在security對象中存入登入者訊息。");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        log.info("3. 根據登入訊息獲取Token.");
        // 需使用jwt來生成token
        String token = tokenUtil.generateToken(userDetails);
        Map<String, String> map = new HashMap<>(4);
        map.put("token", token);
        
        //帶入使用者資訊，權限，角色
        StringBuffer sb = new StringBuffer(0);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        int count = 0;
        for (GrantedAuthority authority : authorities) {
            if (count == 0) sb.append(authority.getAuthority());
            else sb.append(";" + authority.getAuthority());
            count++;
        }
        map.put("username", userDetails.getUsername());
        map.put("roles", sb.toString());

        return CommonResult.success(map, "登入驗證成功!");
    }

    @Override
    public SysUser getSysUser(String username) {
        if (username == null || username.isEmpty() || username.trim().equals("")) return null;
        SysUser sysUser = sysUserDao.getByUsername(username);
        if (sysUser == null) return null;
        return sysUser;
    }

}
