package com.jt.controller;

/**
 * Created by Administrator on 2019/7/16.
 */

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.util.ObjectMapperUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  //要求返回json数据
public class JSONPController {

    @RequestMapping("/web/testJSONP")
    public JSONPObject testJSONP(String callback){
        ItemCat itemCat = new ItemCat();
        itemCat.setId(1000L).setName("shabiliuyujiang");
        JSONPObject jsonpObject = new JSONPObject(callback, itemCat);
        System.out.println(ObjectMapperUtil.toJson(jsonpObject));

        return jsonpObject;
    }
    /**
     * jsonp返回值结果,必须经过特殊格式封装
     * 调用者::回调函数获取
     * 数据返回:封装数据   callback(JSON串)
     * http://manage.jt.com/web/testJSONP?callback=jQuery1111014714054866032056_1563263670933&_=1563263670934
     */
//    @RequestMapping("web/testJSONP")
//    public String testJSONP(String callback){
//        ItemCat itemCat = new ItemCat();
//        itemCat.setId(1000L).setName("caonima");
//        String json = ObjectMapperUtil.toJson(itemCat);
//        System.out.println(callback+"("+json+")");
//        return callback+"("+json+")";
//    }
}
