package com.rt.controller;

import com.rt.domain.ResponseResult;
import com.rt.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 友链(Link)表控制层
 *
 * @author rt
 * @since 2025-03-31 17:20:02
 */
@RestController
@RequestMapping("link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 查询所有审核通过的友链
     * @return
     */
    @GetMapping("/getAllLink")
    public ResponseResult getAllLink(){
        return linkService.getAllLink();
    }
}

