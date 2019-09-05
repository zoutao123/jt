package com.jt.controller;

import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

/**
 * Created by Administrator on 2019/7/3.
 */
@RestController
@RequestMapping("/item")
public class ItemCatController {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/cat/queryItemName")
    public String findNameById(Integer itemCatId){
        return itemCatService.findNameById(itemCatId);
    }


}
