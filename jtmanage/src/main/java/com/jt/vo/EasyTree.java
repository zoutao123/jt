package com.jt.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.lang.annotation.Target;
import java.util.List;

/** vo是服务器数据与页面进行交互的对象,一般需要转化为json格式
 * Created by Administrator on 2019/7/3.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EasyTree<T> implements Serializable {
    private Integer target=1;
    private Integer id;
    private String text;
    private String name;
    private List<T> children;
    private String state;
    private Integer isParent;
}
