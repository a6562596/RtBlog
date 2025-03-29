package com.rt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rt.domain.ResponseResult;
import com.rt.domain.entity.Article;

/**
 * Service层
 */

public interface ArticleService extends IService<Article> {
    //2. 查询热门文章 封装成ResponseResult返回
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);
}
