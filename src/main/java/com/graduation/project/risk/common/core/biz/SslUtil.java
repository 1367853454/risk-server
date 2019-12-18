package com.graduation.project.risk.common.core.biz;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class SslUtil {

    private static Logger logger = LoggerFactory.getLogger(SslUtil.class);

    public static String sendPost(String url, JSONObject bodys, int timeOut) {
        return sendPost(url, null, bodys, timeOut);
    }

    public static String sendPost(String url, JSONObject headers, JSONObject bodys, int timeOut) {
        return sendPost(url, headers, bodys, timeOut, "application/json");
    }

    public static String sendPost(String url, JSONObject headers, JSONObject bodys, int timeOut, String contentType) {
        try {
            URL u = new URL(url);
            if ("https".equalsIgnoreCase(u.getProtocol())) {
                SslUtil.ignoreSsl();
            }

            URLConnection conn = u.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(timeOut);
            conn.setReadTimeout(timeOut);

            if (!StringUtils.isBlank(contentType)) {
                conn.setRequestProperty("Content-Type", contentType);
            }

            logger.info("send http request: {}", url);

            if (headers != null && !headers.isEmpty()) {
                StringBuilder header = new StringBuilder();
                for (String key : headers.keySet()) {
                    conn.setRequestProperty(key, String.valueOf(headers.get(key)));
                    header.append("&");
                    header.append(key).append("=").append(headers.get(key));
                }
                logger.info("request headers: {}", header.substring(1));
            }

            String params;
            if (StringUtils.equalsIgnoreCase(contentType, "application/json")) {
                params = bodys.toJSONString();
            } else {
                StringBuilder body = new StringBuilder();
                for (String key : bodys.keySet()) {
                    body.append("&");
                    body.append(key).append("=").append(bodys.get(key));
                }
                params = body.substring(1);// dispensable.
            }

            logger.info("request params: {}", bodys);

            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            osw.write(params);
            osw.flush();
            osw.close();

            return IOUtils.toString(conn.getInputStream(), "UTF-8");

        } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
            logger.error("send http request: [{}]，fail：{}", url, ExceptionUtils.getStackTrace(e));
        }
        return null;
    }

    private static void ignoreSsl() throws KeyManagementException, NoSuchAlgorithmException {
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
                return true;
            }
        };
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
    }

    private static void trustAllHttpsCertificates() throws NoSuchAlgorithmException, KeyManagementException {
        TrustManager[] trustAllCerts = new TrustManager[1];
        TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    static class miTM implements TrustManager, X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType) {
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType) {
        }
    }

}
