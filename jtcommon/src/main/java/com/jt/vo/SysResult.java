package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * Created by Administrator on 2019/7/4.
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {
    private Integer status=200;//200表示成功,201表示失败
    private String massage;//服务器提示信息
    private Object data;//服务器的返回的数据

    public static SysResult success(){
        return new SysResult(200,"成功",null);
    }
    public static SysResult success(Object data){
        return new SysResult(200,"成功",data);
    }
    public static SysResult success(String massage,Object data){
        return new SysResult(200,massage,data);
    }
    public static SysResult fail(){
        return new SysResult(201,"失败",null);
    }
}
