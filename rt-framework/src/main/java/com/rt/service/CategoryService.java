package com.rt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rt.domain.ResponseResult;
import com.rt.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author rt
 * @since 2025-03-29 11:30:19
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
