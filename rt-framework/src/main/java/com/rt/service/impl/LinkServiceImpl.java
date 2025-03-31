package com.rt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rt.constants.SystemCanstants;
import com.rt.domain.ResponseResult;
import com.rt.domain.entity.Link;
import com.rt.domain.vo.GetAllLinkVo;
import com.rt.mapper.LinkMapper;
import com.rt.service.LinkService;
import com.rt.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2025-03-31 17:20:05
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    /**
     * 查询所有审核通过的友链
     * @return
     */
    @Override
    public ResponseResult getAllLink() {
        //查询出所有link数据
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //审核通过的
        linkLambdaQueryWrapper.eq(Link::getStatus, SystemCanstants.LINK_STATUS_NORMAL);
        //转换成list形式
        List<Link> list = list(linkLambdaQueryWrapper);
        //封装vo
        List<GetAllLinkVo> getAllLinkVos = BeanCopyUtils.copyBeanList(list, GetAllLinkVo.class);

        return ResponseResult.okResult(getAllLinkVos);
    }
}
