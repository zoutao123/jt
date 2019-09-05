package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/7/3.
 */
@Data
@ToString
@Accessors(chain = true)
@TableName("tb_item_cat")
public class ItemCat extends BasePojo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long parentId;
    private String name;
    private Integer status;//状态
    private Integer sortOrder;//商品分类的排序号
    private Boolean isParent;//是否为父级
}
