package com.rt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rt.constants.SystemCanstants;
import com.rt.domain.ResponseResult;
import com.rt.domain.entity.Article;
import com.rt.domain.vo.ArticleListVo;
import com.rt.domain.vo.HotArticleVo;
import com.rt.domain.vo.PageVo;
import com.rt.mapper.ArticleMapper;
import com.rt.service.ArticleService;
import com.rt.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //首页
        // 查询所有的文章
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        // 状态是正式发布的文章
        queryWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);
        // 置顶的字段按降序排列
        queryWrapper.orderByDesc(Article::getIsTop);
        // 如果有categoryId就要查询时和传入相同
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,Article::getCategoryId,categoryId);
        // 分页
        Page<Article> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);

        //封装vo
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);
        //分页vo
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

}
