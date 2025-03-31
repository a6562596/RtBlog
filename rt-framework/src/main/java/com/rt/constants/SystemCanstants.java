package com.rt.constants;

/**
 * @author rt
 */
//字面值(代码中的固定值)处理，把字面值都在这里定义成常量
public class SystemCanstants {

    /**
     *  文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    
    /**
     *  文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;
    
    /**
     * 文章列表当前查询页数
     */
    public static final int ARTICLE_STATUS_CURRENT = 1;

    /**
     * 文章列表每页显示的数据条数
     */
    public static final int ARTICLE_STATUS_SIZE = 10;

    /**
     * 文章的状态
     */
    public static final String STATUS_NORMAL = "0";

    /**
     * 友链的状态审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
     */
    public static final String LINK_STATUS_NORMAL = "0";

    //public static final String LINK_STATUS_NORMAL = "1";

    //public static final String LINK_STATUS_NORMAL = "2";

}