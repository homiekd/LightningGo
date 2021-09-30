package com.kdwu.lightninggo.pages;

import lombok.Data;

/**
 * 權限分頁物件
 */
@Data
public class SysPermissionPage {
    public String name;
    public String code;
    public Integer pageIndex = 1; //預設1
    public Integer pageSize = 10; //預設10
}
