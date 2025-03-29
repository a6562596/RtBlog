package com.rt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rt.constants.SystemCanstants;
import com.rt.domain.ResponseResult;
import com.rt.domain.entity.Article;
import com.rt.domain.entity.Category;
import com.rt.domain.vo.CategoryVo;
import com.rt.mapper.CategoryMapper;
import com.rt.service.ArticleService;
import com.rt.service.CategoryService;
import com.rt.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 分类表(Category)表服务实现类
 *
 * @author rt
 * @since 2025-03-29 11:30:20
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    ArticleService articleService;

    /**
     * 页面上需要展示分类列表,用户可以点击具体的分类查看该分类下面的文章列表
     * 1. 只要求有发布正式文章的分类 2. 必须是正常状态的
     * @return
     */
    @Override
    public ResponseResult getCategoryList() {
        //查询文章列表
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //过滤正常状态的
        queryWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);
        //放入List中
        List<Article> articleList = articleService.list(queryWrapper);

        //stream流
        //1. 找到文章表分类id(去重)
        Set<Long> categoryIds = articleList.stream()
                //找到文章的id
                .map(article -> article.getCategoryId())
                //去重
                .collect(Collectors.toSet());

        //2. 去查询分类表分类id对应的分类名称,并且是正常状态的

        //用文章的id找到对应分类表的数据集合
        List<Category> categories = listByIds(categoryIds);
        //查询正常状态的
        categories = categories.stream()
                .filter(category -> SystemCanstants.STATUS_NORMAL.equals(category.getStatus()))
                //返回给list集合
                .collect(Collectors.toList());

        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);

        return ResponseResult.okResult(categoryVos);
    }
}
