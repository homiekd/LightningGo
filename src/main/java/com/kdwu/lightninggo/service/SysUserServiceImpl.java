package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.dao.SysUserDao;
import com.kdwu.lightninggo.model.*;
import com.kdwu.lightninggo.pages.SysUserPage;
import com.kdwu.lightninggo.utils.JwtTokenUtil;
import com.kdwu.lightninggo.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
    public CommonResult add(SysUser sysUser) {
        String encode = passwordEncoder.encode(sysUser.getPassword()); // 密碼需加密
        sysUser.setPassword(encode);
        sysUserDao.save(sysUser);
        return CommonResult.success("新增用戶成功！");
    }

    @Override
    public CommonResult delete(Integer id) {
        sysUserDao.deleteById(id);
        return CommonResult.success("刪除用戶成功！");
    }

    @Override
    public CommonResult update(SysUser sysUser) {
        sysUserDao.saveAndFlush(sysUser);
        return CommonResult.success("修改用戶成功！");
    }

    @Override
    public SysUser findByPrimaryKey(Integer id) {
        return sysUserDao.findById(id).get();
    }

    @Override
    public Iterable<SysUser> getAllUsers() {
        return sysUserDao.findAll() ;
    }

    //------------------------------------- 實作 method -----------------------------------------------------

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
        Map<String, Object> map = new HashMap<>(4);
        map.put("token", token);

        // 帶入使用者資訊，權限，角色
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

        List<SysPermission> permissionTree = getPermissionTree((SysUser) userDetails);
        map.put("permissionTree: ", permissionTree);

        return CommonResult.success(map, "登入驗證成功!");
    }

    @Override
    public SysUser getSysUser(String username) {
        if (username == null || username.isEmpty() || username.trim().equals("")) return null;
        SysUser sysUser = sysUserDao.getByUsername(username);
        if (sysUser == null) return null;
        return sysUser;
    }

    @Override
    public PageUtil<SysUser> pageBySysUser(SysUserPage sysUserPage) {
        Pageable pageable = PageRequest.of(sysUserPage.getPageIndex() - 1, sysUserPage.getPageSize(), Sort.Direction.ASC, "id");

        String username = sysUserPage.getUsername();
        String cnName = sysUserPage.getCnName();
        String email = sysUserPage.getEmail();

        Page<SysUser> pageBySysUser;
        if (username.isEmpty() && cnName.isEmpty() && email.isEmpty()){
            pageBySysUser = sysUserDao.findAll(pageable);
        }else {
            pageBySysUser = sysUserDao.findAllBySearchContext(username, cnName, email , pageable);
        }

        PageUtil<SysUser> pageUtil = new PageUtil();
        pageUtil.setData(pageBySysUser.getContent());
        pageUtil.setTotalCount(pageBySysUser.getTotalElements());
        return pageUtil;
    }

    @Override
    public CommonResult getUserRolesName(Integer uid) {
        Optional<SysUser> userById = sysUserDao.findById(uid);
        List<String> data = new ArrayList<>(0);
        if (userById.isPresent()) {
            Set<SysUserRole> userRoles = userById.get().getSysUserRoles();
            if (!CollectionUtils.isEmpty(userRoles)) {
                data = userRoles.stream()
                        .map(SysUserRole::getRole)
                        .map(SysRole::getName)
                        .collect(Collectors.toList());
            }
        }

        return CommonResult.success(data, "取出使用者所有角色");
    }

    //------------------------------------- other method -----------------------------------------------------

    /**
     * 遞迴產生樹狀權限並封裝
     * @param sysUser
     * @return
     */
    private List<SysPermission> getPermissionTree(SysUser sysUser){
        // 當前用戶取使用者角色表
        Set<SysUserRole> userRoles = sysUser.getSysUserRoles();
        // CollectionUtils.isEmpty => 判斷集合是不是null或為空
        if (!CollectionUtils.isEmpty(userRoles)){
            // 收集取得使用者所擁有的角色的所有權限，並去除重複權限，且available為true，最終拿到整理排序過的權限表。
            List<SysPermission> allPermission = userRoles.stream()
                                        .map(SysUserRole::getRole)
                                        .flatMap(sysRole -> sysRole.getSysRolePermissions().stream())
                                        .filter(distinctByPermissionID(rolePermission -> rolePermission.getPermissionNo()))
                                        .filter(sysRolePermission -> sysRolePermission.getSysPermission().getAvailable())
                                        .map(SysRolePermission::getSysPermission)
                                        .sorted(Comparator.comparingInt(SysPermission::getLevel).thenComparingInt(SysPermission::getSort))
                                        .collect(Collectors.toList());
            // 參數0: 表示根節點的父ID
            return transferPermissions(allPermission, 0);
        }
        return null;
    }

    /**
     * distinctByPermissionID()方法 使用ConcurrentHashMap來維護Predicate實例，
     * 使用對象屬性來進行去重複。
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private static <T>Predicate<T> distinctByPermissionID(Function<? super T, ?> keyExtractor) {
        // ConcurrentHashMap是一個執行緒安全的Hash Table,它的主要功能是提供了一組和HashTable功能相同但是執行緒安全的方法。
        // ConcurrentHashMap可以做到讀取資料不加鎖,並且其內部的結構可以讓其在進行寫操作的時候能夠將鎖的粒度保持地儘量地小,不用對整個ConcurrentHashMap加鎖。
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        // putIfAbsent() 會先判斷指定的（key）是否存在，則將鍵/值鍵對插入到HashMap中。
        // Function<T,R>的apply(T t)用來接收一個參數，及回傳指定型態的結果。
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    /**
     * 封裝權限父+子層
     * @param allPermission
     * @param parentId
     * @return
     */
    private List<SysPermission> transferPermissions(List<SysPermission> allPermission, Integer parentId) {
        List<SysPermission> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(allPermission)) {
            for (SysPermission permission : allPermission) {
                if (parentId.intValue() == permission.getParentId().intValue()) {
                    SysPermission permissionVo = new SysPermission();
                    BeanUtils.copyProperties(permission, permissionVo);
                    //遞迴查詢子權限，並封裝起來
                    List<SysPermission> childList = transferPermissions(allPermission, permission.getId());
                    if (!CollectionUtils.isEmpty(childList)) {
                        permissionVo.setChildPermissions(childList);
                    }
                    resultList.add(permissionVo);
                }
            }
        }
        return resultList;
    }
}
