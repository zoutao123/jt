package com.jt;

import com.jt.util.ObjectMapperUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Administrator on 2019/7/15.
 */
public class TestHttpClient {
    /**
     * 编码思路:
     * 1.创建工具API对象
     * 2.定义远程url地址
     * 3.定义请求类型对象
     * 4.发起http请求,获取响应结果
     * 5.从响应对象中获取数据
     */
    @Test
    public void testGet() throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "https://item.jd.com/100002981290.html";
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        //从中获取响应数据 JSON
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            String result = EntityUtils.toString(httpResponse.getEntity());
            System.out.println(result);
        } else {
            System.out.println("请求失败！");
        }
    }
}
