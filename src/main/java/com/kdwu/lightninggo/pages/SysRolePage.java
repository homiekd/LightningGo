package com.kdwu.lightninggo.pages;

import lombok.Data;

/**
 * 角色分頁物件
 */
@Data
public class SysRolePage {
    public String name;
    public String description;
    public Integer pageIndex = 1; //預設1
    public Integer pageSize = 10; //預設10
}
