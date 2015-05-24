package com.example.administrator.myprojest103;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by Administrator on 15-5-19.
 */
public class HttpUtil {
    //创建httpclient对象
    public static HttpClient httpClient = new DefaultHttpClient();


    //@param url 发送请求
    //@return 服务器响应字符串
    public static String getRequest(final String url) throws Exception {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        //创建httpget对象
                        HttpGet get = new HttpGet(url);
                        //发送get请求
                        HttpResponse httpResponse = httpClient.execute(get);
                        //如果服务器成功地返回响应
                        if (httpResponse.getStatusLine().getStatusCode() == 200) {
                            String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                            return result;
                        }
                        return null;
                    }
                }
        );
        new Thread(task).start();
        return task.get();
    }

    //@param url 发送请求
    //@param params 请求参数
    //@return 服务器响应字符串
    public static String postRequest(final String url, final Map<String, String> rawParams) throws Exception {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        //创建httppost对象
                        HttpPost post = new HttpPost(url);
                        //若参数个数较多，可对参数进行封装
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        for (String key : rawParams.keySet()) {
                            //封装请求参数
                            params.add(new BasicNameValuePair(key, rawParams.get(key)));
                        }
                        //设置请求参数
                        post.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
                        //发送post请求
                        HttpResponse httpResponse = httpClient.execute(post);
                        //如果服务器成功地返回响应
                        if (httpResponse.getStatusLine().getStatusCode() == 200) {
                            String result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                            return result;
                        }
                        return null;
                    }
                }
        );
        new Thread(task).start();
        return task.get();
    }
}
