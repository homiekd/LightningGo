package com.kdwu.lightninggo.pages;

import lombok.Data;

/**
 * 用戶分頁物件
 */
@Data
public class SysUserPage{
    public String username;
    public String cnName;
    public String email;
    public Integer pageIndex = 1; //預設1
    public Integer pageSize = 10; //預設10
}
