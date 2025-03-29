package com.rt.controller;

import com.rt.domain.ResponseResult;
import com.rt.domain.entity.Article;
import com.rt.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller控制层
 */
//响应都是在响应体中,返回json
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test() {
//        return articleService.list();
//    }
    //1. 查询热门文章 封装成ResponseResult返回
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        ResponseResult result = articleService.hotArticleList();
        return result;
    }

    //
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }
}
