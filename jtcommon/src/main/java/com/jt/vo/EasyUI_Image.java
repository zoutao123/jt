package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019/7/5.
 */
@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class EasyUI_Image {
    private Integer error = 0;//表示用户上传问价是否有错
    private String url;//表示图片的虚拟路径
    private Integer width;//宽度
    private Integer height;//高度
    //多系统之间对象直接传递时必须序列化
}
