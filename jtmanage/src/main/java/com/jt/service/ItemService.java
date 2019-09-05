package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.EasyUI_Table;


public interface ItemService {
    EasyUI_Table findItemByPage(Integer page, Integer rows);

    void saveItem(Item item, ItemDesc itemDesc);

    void updateItem(Item item, ItemDesc itemDesc);

    void instockItem(Long[] ids);

    void reshelfItem(Long[] ids);

    void deleteItem(Long[] ids);

    ItemDesc findDescById(Long id);

    Item findItemById(Long itemId);
}
