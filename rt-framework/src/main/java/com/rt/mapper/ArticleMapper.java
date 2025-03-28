package com.rt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rt.domain.entity.Article;

/**
 * Mapper层
 */
//继承BaseMapper后,无需编写mapper.xml即可crud功能;
public interface ArticleMapper extends BaseMapper<Article> {

}
