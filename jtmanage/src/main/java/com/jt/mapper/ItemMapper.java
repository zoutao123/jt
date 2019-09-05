package com.jt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.Item;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemMapper extends BaseMapper<Item>{
    @Select("select * from tb_item order by updated desc limit #{start},#{rows} ")
	List<Item> findItemById(@Param("start") Integer start,@Param("rows") Integer rows);

    /**
     * mybatis中默认是不允许多值传参
     * 需要用户将多值转化为单值
     * 1.本身数据就是一个参数
     * 2.将数据使用对象进行封装
     * 3.使用集合封装 list array
     * 4.无论参数是什么都可以封装为Map集合 @Param
     */

    void deleteItems(Long[] ids,int a,int b);
}
