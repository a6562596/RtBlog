package com.rt.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//返回前端特定字段
public class CategoryVo {
    private Long id;
    //分类名
    private String name;
}
