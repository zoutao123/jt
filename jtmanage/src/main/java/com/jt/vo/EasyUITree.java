package com.jt.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jt.pojo.ItemCat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2019/7/3.
 */
@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class EasyUITree {
    private Long id;
    private String text;
    private String state;
    private List<EasyUITree> children;
    private Integer isParent;
}
