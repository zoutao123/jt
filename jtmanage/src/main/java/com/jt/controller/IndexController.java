package com.jt.controller;

import com.jt.pojo.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    /**
     * 业务需求:通过url实现页面跳转功能
     *
     * @param moduleName
     * @return
     */
    @RequestMapping("/page/{moduleName}")
    public String module(@PathVariable("moduleName") String moduleName) {

        return moduleName;
    }

    //localhost:8091/saveItem/手机/不关机/10000
//	@RequestMapping("/saveItem/{title}/{sellPoint}/{price}")
//	@ResponseBody
//	public Item saveItem(@PathVariable String title,
//						 @PathVariable String sellPoint,
//						 @PathVariable Long price){
//		Item item = new Item();
//		item.setPrice(price);
//		item.setSellPoint(sellPoint);
//		item.setTitle(title);
//		return item;
//	}
    @RequestMapping("/saveItem/{title}/{sellPoint}/{price}")
    @ResponseBody
    public Item saveItem(Item item) {
        return item;
    }

    /**
     * 测试负载均衡
     *
     */
    @RequestMapping("/get")
    @ResponseBody
    public String getMsg(){
        return "8091";
    }
}
