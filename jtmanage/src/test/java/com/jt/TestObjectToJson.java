package com.jt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.pojo.ItemDesc;
import com.jt.util.ObjectMapperUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2019/7/10.
 */
public class TestObjectToJson {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void toJson(){
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(1000L).setItemDesc("我是test").setCreated(new Date()).setUpdated(new Date());
        try {
            String jsonObject = mapper.writeValueAsString(itemDesc);
            System.out.println(jsonObject);
            //~~~~~~~~~~~
            //将json转化为对象类型
            ItemDesc itemDesc1 = mapper.readValue(jsonObject, ItemDesc.class);
            System.out.println(itemDesc1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void TransTest(){
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(1000L).setItemDesc("我是test").setCreated(new Date()).setUpdated(new Date());
        String json = ObjectMapperUtil.toJson(itemDesc);
        System.out.println(json);
        ItemDesc itemDesc1 = ObjectMapperUtil.toObject(json, ItemDesc.class);
        System.out.println(itemDesc1);
    }
}
