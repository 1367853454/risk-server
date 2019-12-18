package com.graduation.project.risk.common.core.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

public class HttpUtil {

    private Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static final String MIME_TYPE = "application/json";

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();

    private static HttpUtil instance = null;

    private HttpUtil() {
    }

    public static HttpUtil getInstance() {
        if (instance == null) {
            instance = new HttpUtil();
        }
        return instance;
    }

    /**
     * GET method
     *
     * @param url
     * @return response
     */
    public String doGet(String url) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(url);
            request.setConfig(requestConfig);
            //HttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            }
        } catch (Exception e) {
            logger.error("Do get method failed，url={}", url, e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {

            }
        }

        return null;
    }

    /**
     * POST method
     *
     * @param url
     * @param json
     * @return
     */
    public String doPost(String url, JSONObject json) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        logger.info("POST：url={}, params={}", url, json);

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);

            StringEntity entity = new StringEntity(
                    json.toString(),
                    ContentType.create(MIME_TYPE, DEFAULT_CHARSET));
            httpPost.setEntity(entity);

            response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);

            JSONObject data = JSON.parseObject(result);

            return data.toString();

        } catch (Exception e) {
            logger.error("Do post method failed，url={}, param={}", url, json.toJSONString(), e);
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {

            }
        }
        return null;
    }

    /**
     * POST method
     *
     * @param url
     * @param json
     * @return
     */
    public String doPost(String url, String json) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);

            StringEntity entity = new StringEntity(json, ContentType.create(MIME_TYPE, DEFAULT_CHARSET));
            httpPost.setEntity(entity);

            response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);

            JSONObject data = JSON.parseObject(result);

            return data.toString();

        } catch (Exception e) {
            logger.error("Do post method failed，url={}, param={}", url, json, e);
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
            } catch (IOException e) {

            }
        }
        return null;
    }

    /**
     * concat url
     *
     * @param url
     * @param params
     * @return
     */
    public static String getRqstUrl(String url, Map<String, String> params) {
        StringBuilder builder = new StringBuilder(url);
        boolean isFirst = true;
        for (String key : params.keySet()) {
            if (key != null && params.get(key) != null) {
                if (isFirst) {
                    isFirst = false;
                    builder.append("?");
                } else {
                    builder.append("&");
                }
                builder.append(key)
                        .append("=")
                        .append(params.get(key));
            }
        }
        return builder.toString();
    }


    public static void main(String[] args) {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("username","username");
        map.put("password","password");
        System.out.println(getRqstUrl("www.google.com", map));
    }

    /**
     * GET method
     *
     * @param url
     * @return
     */
    public static String doGetByToken(String url, String token) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(url);
            request.setConfig(requestConfig);
            request.setHeader("Authorization", "token " + token);
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            }
        } catch (Exception e) {

        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {

            }
        }

        return null;
    }


    public static String doGetByAuth(String url, String auth) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet request = new HttpGet(url);
            request.setConfig(requestConfig);
            request.setHeader("Authorization", auth);
            HttpResponse response = httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            }
        } catch (Exception e) {

        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {

            }
        }

        return null;
    }
}
