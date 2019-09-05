package com.jt.util;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2019/7/15.
 */
@SuppressWarnings("ALL")
@Service
public class HttpClientService {

    @Autowired
    private CloseableHttpClient httpClient;
    @Autowired
    private RequestConfig requestConfig;

    /**
     * url----String----Json
     * 发起请求获取服务器数据
     * 参数说明
     * 1.url地址
     * 2.用户提交的参数使用Map封装
     * 3.指定编码格式
     * <p>
     * 步骤：
     * 1.校验字符集,如果字符集为null 设定一个默认值
     * 2.校验params是否为null
     * null:表示用户get请求无需传参
     * !null:表示需要传参 get请求的规则 url?key=value&key1=value1...
     *
     * @param url
     * @param params
     * @param charset
     * @return
     */
    public String doGet(String url, Map<String, String> params,
                        String charset) {

        //1.校验字符集
        if (StringUtils.isEmpty(charset)) {
            charset = "UTF-8";
        }

        /**
         * 2.校验params参数是否为空
         *  url:www.jt.com?id=1&name=xxx
         */
        if (params != null) {
            url += "?";
            //2.1 遍历map集合
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                url = url + key + "=" + value + "&";
            }
            //2.2经过循环遍历 最终url多个&
            url = url.substring(0, url.length() - 1);
        }

        //3.发起get请求
        HttpGet httpGet = new HttpGet(url);
        //定义请求的超时时间
        httpGet.setConfig(requestConfig);
        String result = null;
        try {
            CloseableHttpResponse response =
                    httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(response.getEntity(), charset);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String doGet(String url) {
        return doGet(url, null, null);
    }

    public String doGet(String url, Map<String, String> params) {
        return doGet(url, params, null);
    }

}
