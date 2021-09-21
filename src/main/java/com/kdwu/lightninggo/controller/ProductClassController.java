package com.kdwu.lightninggo.controller;

import com.kdwu.lightninggo.common.CommonResult;
import com.kdwu.lightninggo.model.ProductClass;
import com.kdwu.lightninggo.service.ProductClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("ProductClass")
@Api(value = "商品分類控制器")
public class ProductClassController {

    @Autowired
    private ProductClassService productClassService;

    @ApiOperation(value = "商品分類表分頁")
    @GetMapping("/FindPage")
    public Page<ProductClass> findPage(@RequestParam(value = "PageIndex", defaultValue = "0") int pageIndex,
                                       @RequestParam(value = "PageSize", defaultValue = "10") int pageSize) {
        return productClassService.pageByProductClass(pageIndex, pageSize);
    }

    @ApiOperation(value = "新增商品分類")
    @PostMapping("/Add")
    public CommonResult add(@RequestBody ProductClass productClass) {
        return productClassService.add(productClass);
    }

    @ApiOperation(value = "修改商品分類")
    @PostMapping("/Update")
    public CommonResult update(@RequestBody ProductClass productClass) {
        return productClassService.update(productClass);
    }

    @ApiOperation(value = "刪除商品分類")
    @DeleteMapping("/Delete/{id}")
    public CommonResult delete(@PathVariable("id") Integer id) {
        return productClassService.delete(id);
    }
}
