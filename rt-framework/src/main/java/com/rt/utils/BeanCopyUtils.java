package com.rt.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {

    private BeanCopyUtils(){

    }

    /**
     * 单个实体类的拷贝
     * @param O 有数据的对象
     * @param clazz 字节码反射
     * @return 结果
     */
    public static <V> V copyBean(Object O, Class<V> clazz){
        //创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
            //实现属性拷贝
            BeanUtils.copyProperties(O,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return result;
    }

    /**
     *
     * @param list 传入的是集合类型的实体类
     * @param clazz 字节码反射
     * @return 集合的结果
     * @param <V>
     */
    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz){
        //将 List<源类型> 转换为 Stream<源类型>，启用流式操作。
        return list.stream()
                //对每个元素 o 调用 copyBean(o, clazz)，生成 目标类型 的新对象。
                .map(o -> copyBean(o, clazz))
                //将流中的新对象收集到 List<目标类型> 中。
                .collect(Collectors.toList());
    }

}
