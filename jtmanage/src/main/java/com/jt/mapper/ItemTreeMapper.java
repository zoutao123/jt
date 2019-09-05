package com.jt.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.pojo.ItemCat;
import com.jt.vo.EasyTree;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2019/7/3.
 */
public interface ItemTreeMapper extends BaseMapper<ItemCat>{
    @Select("select id,name,is_parent from tb_item_cat where parent_id=#{id}")
    List<EasyTree> findTree(Long id);
}
