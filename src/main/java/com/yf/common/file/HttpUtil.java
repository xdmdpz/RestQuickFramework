package com.yf.common.file;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * http请求工具类
 * <p>
 * Created by li on 2016/10/18.
 */
public class HttpUtil {

    public static CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
    public static HttpClient httpClient = new HttpClient();

    /**
     * rawPost的请求方法，将图片保存在相应的服务器上，并返回结果
     *
     * @param url
     * @param headers
     * @param baos
     * @return
     */
    public static String binaryPost(String url, Map<String, String> headers, ByteArrayOutputStream baos) {
        //post请求方式请求该url
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response = null;
        //添加请求头信息
        for (String key : headers.keySet()) {
            httpPost.setHeader(key, headers.get(key));
        }

        try {
            //设置请求的参数实体
            httpPost.setEntity(new ByteArrayEntity(baos.toByteArray()));
            //请求响应
            response = closeableHttpClient.execute(httpPost);
            //获取响应信息
            return getResponse(response);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * rawPost的请求方法，将图片保存在相应的服务器上，并返回结果
     *
     * @param url
     * @param headers
     * @param body
     * @return
     */
    public static String rawPost(String url, Map<String, String> headers, String body) {
        //post请求方式请求该url
        PostMethod postMethod = new PostMethod(url);
        //添加请求头信息
        for (String key : headers.keySet()) {
            postMethod.setRequestHeader(key, headers.get(key));
        }
        try {
            //设置请求体
            postMethod.setRequestBody(body);
            //请求响应
            int statusCode = httpClient.executeMethod(postMethod);
            System.out.println(statusCode);
            if(statusCode == 200) {
                return postMethod.getResponseBodyAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPost(String url, Map<String, String> headers, String body) {
        HttpPost httpPost = new HttpPost(url);
        for (String key : headers.keySet()) {
            httpPost.setHeader(key, headers.get(key));
        }
        httpPost.setEntity(new StringEntity(body, Charset.forName("UTF-8")));
        CloseableHttpResponse response = null;
        try {
            response = closeableHttpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doPost(String url, Map<String, String> headers, List<NameValuePair> pairs) {
        HttpPost httpPost = new HttpPost(url);
        for (String key : headers.keySet()) {
            httpPost.setHeader(key, headers.get(key));
        }
        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
            response = closeableHttpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doGet(String url, List<NameValuePair> nameValuePairList) {
        try {
            url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(nameValuePairList), "UTF-8");
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Authorization", "Basic QURNSU46S1lMSU4=");
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            return getResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String doDelete(String url) {
        try {
            HttpDelete httpDelete = new HttpDelete(url);
            httpDelete.setHeader("Authorization", "Basic QURNSU46S1lMSU4=");
            CloseableHttpResponse response = closeableHttpClient.execute(httpDelete);
            return getResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String rawPut(String url, Map<String, String> headers, String body) {

        //post请求方式请求该url
        PutMethod putMethod = new PutMethod(url);
        //添加请求头信息
        for (String key : headers.keySet()) {
            putMethod.setRequestHeader(key, headers.get(key));
        }
        try {
            //设置请求体
            putMethod.setRequestBody(body);
            //请求响应
            int statusCode = httpClient.executeMethod(putMethod);
            System.out.println(statusCode);
            if(statusCode == 200) {
                return putMethod.getResponseBodyAsString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getResponse(CloseableHttpResponse response) {
        try {
            //获取响应信息
            HttpEntity entity = response.getEntity();
            //对响应码值进行判断
            if (response.getStatusLine().getStatusCode() != 200) {
                return null;
            }
            //获取请求后返回的结果
            String result = EntityUtils.toString(entity);
            //释放资源
            EntityUtils.consume(entity);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
