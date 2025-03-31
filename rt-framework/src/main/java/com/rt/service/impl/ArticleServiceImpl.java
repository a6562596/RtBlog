package com.rt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rt.constants.SystemCanstants;
import com.rt.domain.ResponseResult;
import com.rt.domain.entity.Article;
import com.rt.domain.entity.Category;
import com.rt.domain.vo.ArticleDetailVo;
import com.rt.domain.vo.ArticleListVo;
import com.rt.domain.vo.HotArticleVo;
import com.rt.domain.vo.PageVo;
import com.rt.mapper.ArticleMapper;
import com.rt.service.ArticleService;
import com.rt.service.CategoryService;
import com.rt.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Service实现类
 */
//ServiceImpl<M, T>用于快速构建 Service 层的 CRUD 操作
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    //3. 查询热门文章 封装成ResponseResult返回
    @Override
    public ResponseResult hotArticleList() {
        //封装查询条件
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        queryWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);
        //非逻辑删除的数据(已经在application.yml中配置过了)
        //按照浏览量排序
        queryWrapper.orderByDesc(Article::getViewCount);
        //最多查询10条消息
        Page<Article> page = new Page(SystemCanstants.ARTICLE_STATUS_CURRENT,SystemCanstants.ARTICLE_STATUS_SIZE);
        page(page,queryWrapper);

        List<Article> articles = page.getRecords();
        //bean拷贝,只需要返回部分字段
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
//        List<HotArticleVo> hotArticleVos = new ArrayList<>();
//        //循环每一个实体类成员
//        for (Article article : articles) {
//            //需要的类成员
//            HotArticleVo vo = new HotArticleVo();
//            //把有数据的类(articles)成员 -> 没有数据的类(vo)成员
//            BeanUtils.copyProperties(article,vo);
//            //循环添加到list集合中
//            hotArticleVos.add(vo);
//        }

        return ResponseResult.okResult(vs);
    }

    /**
     * 查询首页文章信息,分类文章信息
     */
    @Autowired
    //注入我们写的CategoryService接口
    private CategoryService categoryService;
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //首页
        // 查询所有的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 状态是正式发布的文章
        queryWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);
        // 置顶的字段按降序排列
        queryWrapper.orderByDesc(Article::getIsTop);
        //判空。如果前端传了categoryId这个参数，那么查询时要和传入的相同。第二个参数是数据表的文章id，第三个字段是前端传来的文章id
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,Article::getCategoryId,categoryId);
        // 分页
        Page<Article> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        /**
         * 解决categoryName字段没有返回值的问题。在分页之后，封装成ArticleListVo之前，进行处理
         */
/*        //用categoryId来查询categoryName(category表的name字段)，也就是查询'分类名称'。有两种方式来实现，如下
        List<Article> articles = page.getRecords();
        //第一种方式，用for循环遍历的方式
        for (Article article : articles) {
            //1.获取到文章表id--->查询分类表的数据--->把分类表的名称数据给文章表的分类名称
            //'article.getCategoryId()'表示从article表获取category_id字段，然后作为查询category表的name字段
            Category category = categoryService.getById(article.getCategoryId());
            //把查询出来的category表的name字段值，也就是article，设置给Article实体类的categoryName成员变量
            article.setCategoryName(category.getName());
        }*/

        /**
         * 解决categoryName字段没有返回值的问题。在分页之后，封装成ArticleListVo之前，进行处理。第二种方式，用stream流的方式
         */
        //用categoryId来查询categoryName(category表的name字段)，也就是查询'分类名称'
        List<Article> articles = page.getRecords();
        articles.stream()
                .map(article -> {
                    //'article.getCategoryId()'表示从article表获取category_id字段，然后作为查询category表的name字段
                    Category category = categoryService.getById(article.getCategoryId());
                    String name = category.getName();
                    //把查询出来的category表的name字段值，也就是article，设置给Article实体类的categoryName成员变量
                    article.setCategoryName(name);
                    //把查询出来的category表的name字段值，也就是article，设置给Article实体类的categoryName成员变量
                    return article;
                })
                .collect(Collectors.toList());

        //封装vo
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        //分页vo
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 阅读全文
     * @param id
     * @return
     */
    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章正文
        Article article = getById(id);
        //转换vo
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名称
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category != null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }

}
