package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.ProductBrand;
import com.kdwu.lightninggo.model.ProductClass;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductClassService {
    public CommonResult add(ProductClass productClass);

    public CommonResult delete(Integer id);

    public CommonResult update(ProductClass productClass);

    public List<ProductClass> getAllProductClass();

    public Page<ProductClass> pageByProductClass(Integer page, Integer size);
}
