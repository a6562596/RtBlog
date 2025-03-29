package com.rt.controller;

import com.rt.domain.ResponseResult;
import com.rt.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分类表(Category)表控制层
 *
 * @author rt
 * @since 2025-03-29 11:30:17
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    /**
     * 服务对象
     */
    @Autowired
    private CategoryService categoryService;

    /**
     * @return 获取分类
     */
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList() {
        return categoryService.getCategoryList();

    }

}

