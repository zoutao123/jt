package com.jt.service.impl;

import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.service.ItemService;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/7/16.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private HttpClientService httpClientService;

    @Override
    public Item findItemById(Long itemId) {
        String url = "http://manage.jt.com/web/item/findItemById/"+ itemId;
        String itemJson = httpClientService.doGet(url);
        return ObjectMapperUtil.toObject(itemJson,Item.class);
    }

    @Override
    public ItemDesc findDescById(Long itemId) {
        String url = "http://manage.jt.com/web/item/findDescById/"+ itemId;
        String itemJson = httpClientService.doGet(url);
        return ObjectMapperUtil.toObject(itemJson,ItemDesc.class);
    }
}
