package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.pojo.ItemCat;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUI_Table;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jt.service.ItemService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 根据分页信息实现查询
     * <p>
     * springMVC 通过request.getparment("参数名")从请求头中查找数据
     * 在把数据类型根据定义的参数类型进行转换
     */
    @RequestMapping("/query")
    public EasyUI_Table findItemByPage(Integer page, Integer rows) {
        return itemService.findItemByPage(page, rows);
    }

    /**
     * 在controller控制处理
     *
     * @param item
     * @return
     */
    @RequestMapping("/save")
    public SysResult saveItem(Item item, ItemDesc itemDesc) {
//		try {
//			itemService.saveItem(item);
//			return SysResult.success();
//		}catch (Exception e){
//			return SysResult.fail();
//		}
        itemService.saveItem(item, itemDesc);
        return SysResult.success();
    }

    /**
     * 根据商品id号,查询商品详情信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/query/item/desc/{id}")
    public SysResult findDescById(@PathVariable("id") Long id) {
        ItemDesc itemDesc = itemService.findDescById(id);
        return SysResult.success(itemDesc);
    }

    @RequestMapping("/update")
    public SysResult updateItem(Item item, ItemDesc itemDesc) {
//        itemService.updateItem(item);
//        itemDesc.setItemId(item.getId());
//        itemService.updateItemDesc(itemDesc);
//        return SysResult.success();

        itemService.updateItem(item, itemDesc);
        return SysResult.success();
    }

    @RequestMapping("/instock")
    public SysResult instockItem(Long[] ids) {

        itemService.instockItem(ids);

        return SysResult.success();
//        int status=2;
//        itemMapper.updateStatus(ids,status);
    }

    @RequestMapping("/reshelf")
    public SysResult reshelfItem(Long[] ids) {

        itemService.reshelfItem(ids);

        return SysResult.success();
    }

    /**
     * 如果参数是通过,号分割,泽接受是可以使用数组接收
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public SysResult deleteItem(Long[] ids) {

        itemService.deleteItem(ids);

        return SysResult.success();
    }
}
