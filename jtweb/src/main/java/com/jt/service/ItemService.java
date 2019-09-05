package com.jt.service;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;

/**
 * Created by Administrator on 2019/7/16.
 */
public interface ItemService {
    Item findItemById(Long itemId);

    ItemDesc findDescById(Long itemId);
}
