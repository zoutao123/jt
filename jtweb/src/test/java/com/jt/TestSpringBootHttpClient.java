package com.jt;

import com.jt.util.HttpClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2019/7/15.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSpringBootHttpClient {
    @Autowired
    private HttpClientService httpClientService;

    @Test
    public void testGet(){
        String url = "https://item.jd.com/100002981290.html";
        String result =httpClientService.doGet(url);
        System.out.println(result);
    }

}
