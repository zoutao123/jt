package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2019/7/16.
 */
@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;
    /**
     * 编辑jt-web前台服务器
     * 从从后台manage中获取数据,之后页面展现 
     */
    @RequestMapping("/{itemId}")
    public String findItemById(@PathVariable Long itemId, Model model){
        Item item = itemService.findItemById(itemId);
        ItemDesc itemDesc = itemService.findDescById(itemId);
        model.addAttribute("item",item);
        model.addAttribute("itemDesc",itemDesc);
        return "item"; // 跳转的页面
    }

}
