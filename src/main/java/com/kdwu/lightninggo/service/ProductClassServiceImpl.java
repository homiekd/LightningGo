package com.kdwu.lightninggo.service;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.dao.ProductClassDao;
import com.kdwu.lightninggo.model.ProductClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("productClassService")
public class ProductClassServiceImpl implements ProductClassService {

    @Autowired
    private ProductClassDao productClassDao;


    @Override
    public CommonResult add(ProductClass productClass) {
        productClassDao.save(productClass);
        return CommonResult.success("新增商品分類成功！");
    }

    @Override
    public CommonResult delete(Integer id) {
        productClassDao.deleteById(id);
        return CommonResult.success("刪除商品分類成功！");
    }

    @Override
    public CommonResult update(ProductClass productClass) {
        productClassDao.saveAndFlush(productClass);
        return CommonResult.success("修改商品分類成功！");
    }

    @Override
    public List<ProductClass> getAllProductClass() {
        return productClassDao.findAll();
    }

    @Override
    public Page<ProductClass> pageByProductClass(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return productClassDao.findAll(pageable);
    }
}
