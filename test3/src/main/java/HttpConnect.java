import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

public class HttpConnect {
    static{
        objectList = new ArrayList<>();
    }
    public static void getInformation() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 参数
        ArrayList<String> List = new ArrayList<>();
        List.add("China");
        List.add("US");
        List.add("United%20Kingdom");
        List.add("Japan");
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            for (int i = 0; i < 4; i++) {

                HttpGet httpGet = new HttpGet("https://covid-api.mmediagroup.fr/v1/cases?country=" + List.get(i));
                // 配置信息
                RequestConfig requestConfig = RequestConfig.custom()
                        // 设置连接超时时间(单位毫秒)
                        .setConnectTimeout(50000)
                        // 设置请求超时时间(单位毫秒)
                        .setConnectionRequestTimeout(50000)
                        // socket读写超时时间(单位毫秒)
                        .setSocketTimeout(50000)
                        // 设置是否允许重定向(默认为true)
                        .setRedirectsEnabled(true).build();

                // 将上面的配置信息 运用到这个Get请求里
                httpGet.setConfig(requestConfig);
                // 由客户端执行(发送)Get请求
                response = httpClient.execute(httpGet);
                // 从响应模型中获取响应实体
                HttpEntity responseEntity = response.getEntity();
                System.out.println("响应状态为:" + response.getStatusLine());
                if (responseEntity != null) {
                    String result = EntityUtils.toString(responseEntity);
                    System.out.println("响应内容为:" + result);
                    JSONObject object = JSONObject.parseObject(result);
                    objectList.add(object);
                    //System.out.println(object);

                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } finally {
            try {
                // 释放资源
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ArrayList<JSONObject> objectList;


}
