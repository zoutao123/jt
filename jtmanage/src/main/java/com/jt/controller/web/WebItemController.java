package com.jt.controller.web;

import com.jt.annotation.CacheAnnotation;
import com.jt.enu.KEY_ENUM;
import com.jt.mapper.ItemDescMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/7/16.
 */
@RestController
@RequestMapping("/web/item")
public class WebItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/findItemById/{itemId}")
    @CacheAnnotation()
    public Item findItemById(@PathVariable Long itemId){
        Item item = itemService.findItemById(itemId);
        return item;
    }

    @RequestMapping("/findDescById/{itemId}")
    @CacheAnnotation()
    public ItemDesc findDescById(@PathVariable("itemId") Long itemId){
        return itemService.findDescById(itemId);
    }
}
