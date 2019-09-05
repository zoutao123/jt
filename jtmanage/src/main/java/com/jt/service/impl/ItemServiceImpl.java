package com.jt.service.impl;


import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.vo.EasyUI_Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;

    @Transactional
    @Override
    public EasyUI_Table findItemByPage(Integer page, Integer rows) {
        Integer total = itemMapper.selectCount(null);
        Integer start = (page - 1) * rows;
        List<Item> item = itemMapper.findItemById(start, rows);
        EasyUI_Table table = new EasyUI_Table();
        table.setRows(item);
        table.setTotal(total);

        return table;
    }

    /**
     * 商品入库 添加信息到商品表与商品评价表
     * @param item
     * @param itemDesc
     */
    @Transactional
    @Override
    public void saveItem(Item item, ItemDesc itemDesc) {
        item.setStatus(1).setCreated(new Date()).setUpdated(item.getCreated());
        itemMapper.insert(item);
        itemDesc.setItemId(item.getId()).setCreated(item.getCreated()).setUpdated(item.getUpdated());
        itemDescMapper.insert(itemDesc);
    }

    @Transactional
    @Override
    public void updateItem(Item item,ItemDesc itemDesc) {
        item.setUpdated(new Date());
        itemDesc.setItemId(item.getId());
        itemDesc.setUpdated(item.getUpdated());
        itemMapper.updateById(item);
        itemDescMapper.updateById(itemDesc);
//    @Transactional
//    @Override
//    public void updateItemDesc(ItemDesc itemDesc) {
//        itemDesc.setUpdated(new Date());
//        itemDescMapper.updateById(itemDesc);
//    }
    }

    @Transactional
    @Override
    public void instockItem(Long[] ids) {
        Item item = new Item();
        for (int i = 0; i < ids.length; i++) {
            item.setId(ids[i]);
            item.setStatus(2);
            item.setUpdated(new Date());
            itemMapper.updateById(item);
        }
//        Item item = new Item();
//        item.setStatus(2).setUpdated(new Date());
//        List<Long> longList = Arrays.asList(ids);
//        UpdateWrapper<Object> updateWrapper = new UpdateWrapper<>();
//        UpdateWrapper.in("id",longList);
//        itemMapper.update(item,updateWrapper);

    }

    @Transactional
    @Override
    public void reshelfItem(Long[] ids) {
        Item item = new Item();
        for (int i = 0; i < ids.length; i++) {
            item.setId(ids[i]);
            item.setStatus(1);
            item.setUpdated(new Date());
            itemMapper.updateById(item);
        }
    }

    @Transactional
    @Override
    public void deleteItem(Long[] ids) {
        //将数组转为集合
        List<Long> idList = Arrays.asList(ids);
        itemMapper.deleteBatchIds(idList);
        itemDescMapper.deleteBatchIds(idList);
    }

    @Transactional
    @Override
    public ItemDesc findDescById(Long id) {
        ItemDesc itemDesc = itemDescMapper.selectById(id);
        return itemDesc;
    }


    @Override
    public Item findItemById(Long itemId) {
        Item item = itemMapper.selectById(itemId);
        return item;
    }


}
