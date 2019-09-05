package com.jt.controller;

import com.jt.service.ItemTreeService;
import com.jt.vo.EasyTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by Administrator on 2019/7/3.
 */
@RestController
@RequestMapping("/item")
public class ItemTreeController {
    @Autowired
    private ItemTreeService itemTreeService;
    @RequestMapping("/cat/list")
    public List<EasyTree> findTree(){
        Long cid=new Long(0);
//        List<EasyTree> treeList = itemTreeService.findTree(cid);
        List<EasyTree> treeList = itemTreeService.findItemCatByCache(cid);
        return treeList;
    }
}
