package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.dao.ProductBrandDao;
import com.kdwu.lightninggo.model.ProductBrand;
import com.kdwu.lightninggo.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productBrandService")
public class ProductBrandServiceImpl implements ProductBrandService {

    @Autowired
    private ProductBrandDao productBrandDao;


    @Override
    public CommonResult add(ProductBrand productBrand) {
        productBrandDao.save(productBrand);
        return CommonResult.success("新增商品品牌成功！");
    }

    @Override
    public CommonResult delete(Integer id) {
        productBrandDao.deleteById(id);
        return CommonResult.success("刪除商品品牌成功！");
    }

    @Override
    public CommonResult update(ProductBrand productBrand) {
        productBrandDao.saveAndFlush(productBrand);
        return CommonResult.success("修改商品品牌成功！");
    }

    @Override
    public List<ProductBrand> getAllProductBrands() {
        return productBrandDao.findAll();
    }

    @Override
    public PageUtil<ProductBrand> pageByProductBrand(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        Page<ProductBrand> pageByProductBrand = productBrandDao.findAll(pageable);
        PageUtil<ProductBrand> pageUtil = new PageUtil();
        pageUtil.setData(pageByProductBrand.getContent());
        pageUtil.setTotalCount(pageByProductBrand.getTotalElements());
        return pageUtil;
    }
}
