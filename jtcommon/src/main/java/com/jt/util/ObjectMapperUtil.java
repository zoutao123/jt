package com.jt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by Administrator on 2019/7/10.
 */
public class ObjectMapperUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    //1.将对象转为json
    public static String toJson(Object object) {
        String result = null;
        try {
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return result;
    }

    //2.将json转为对象
    public static <T> T toObject(String json, Class<T> objectClass) {
        T object = null;
        try {
            object = mapper.readValue(json, objectClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return object;
    }
}
