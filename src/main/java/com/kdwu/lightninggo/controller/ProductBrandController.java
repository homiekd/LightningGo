package com.kdwu.lightninggo.controller;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.ProductBrand;
import com.kdwu.lightninggo.pages.ProductBrandPage;
import com.kdwu.lightninggo.service.ProductBrandService;
import com.kdwu.lightninggo.utils.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("ProductBrand")
@Api(value = "商品品牌控制器")
public class ProductBrandController {

    @Autowired
    private ProductBrandService productBrandService;

    @ApiOperation(value = "商品品牌表分頁")
    @PostMapping("/FindPage")
    public PageUtil<ProductBrand> findPage(@RequestBody ProductBrandPage productBrandPage) {
        return productBrandService.pageByProductBrand(productBrandPage);
    }

    @ApiOperation(value = "新增商品品牌")
    @PostMapping("/Add")
    public CommonResult add(@RequestBody ProductBrand productBrand) {
        return productBrandService.add(productBrand);
    }

    @ApiOperation(value = "修改商品品牌")
    @PutMapping("/Update")
    public CommonResult update(@RequestBody ProductBrand productBrand) {
        return productBrandService.update(productBrand);
    }

    @ApiOperation(value = "刪除商品品牌")
    @DeleteMapping("/Delete/{id}")
    public CommonResult delete(@PathVariable("id") Integer id) {
        return productBrandService.delete(id);
    }

    @ApiOperation(value = "依id取得單筆商品品牌")
    @GetMapping(value = "/Get/{id}")
    public CommonResult getProductBrand(@PathVariable("id") Integer id){
        ProductBrand byPrimaryKey = productBrandService.findByPrimaryKey(id);
        return CommonResult.success(byPrimaryKey, "取得商品品牌成功！");
    }
}
