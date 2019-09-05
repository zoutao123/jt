package com.jt.service.impl;

import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2019/7/3.
 */
@Service("itemCatServiceImpl")
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;
    @Override
    public String findNameById(Integer id) {
        ItemCat itemCat = itemCatMapper.selectById(id);
        return itemCat.getName();
    }
}
