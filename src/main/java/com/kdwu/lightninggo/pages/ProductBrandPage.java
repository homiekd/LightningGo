package com.kdwu.lightninggo.pages;

import lombok.Data;

import java.util.Date;

/**
 * 商品品牌分頁物件
 */
@Data
public class ProductBrandPage {
    public String code;
    public String name;
    public String description;
    public Boolean state;
    public Date createdDate;
    public Integer pageIndex = 1; //預設1
    public Integer pageSize = 10; //預設10
}
