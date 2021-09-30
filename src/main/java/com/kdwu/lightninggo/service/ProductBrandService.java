package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.ProductBrand;
import com.kdwu.lightninggo.model.SysRole;
import com.kdwu.lightninggo.pages.ProductBrandPage;
import com.kdwu.lightninggo.utils.PageUtil;

import java.util.List;

public interface ProductBrandService {

    public CommonResult add(ProductBrand productBrand);

    public CommonResult delete(Integer id);

    public CommonResult update(ProductBrand productBrand);

    public List<ProductBrand> getAllProductBrands();

    public PageUtil<ProductBrand> pageByProductBrand(ProductBrandPage productBrandPage);

    public ProductBrand findByPrimaryKey(Integer id);
}
