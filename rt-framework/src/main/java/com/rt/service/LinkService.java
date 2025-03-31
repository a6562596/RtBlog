package com.rt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rt.domain.ResponseResult;
import com.rt.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2025-03-31 17:20:04
 */
public interface LinkService extends IService<Link> {

    /**
     * 查询所有审核通过的友链
     * @return
     */
    ResponseResult getAllLink();
}
