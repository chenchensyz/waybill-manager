package cn.com.waybill.tools;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.net.www.protocol.http.Handler;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public class HttpConnection {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnection.class);

    /**
     * 生成uuid
     *
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }

    public static Map<String, Object> httpRequest(String requestUrl, String method, String contentType, String outputStr, Map<String, String> serviceHeader) {
        Map<String, Object> map = Maps.newHashMap();
        String result = null;
        HttpURLConnection conn = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        int code = 402;  //内网返回错误
        try {
            URL url = new URL(null, requestUrl, new Handler());
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            //设置超时
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(50000);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(method);
            if (StringUtils.isNotBlank(contentType)) {
                conn.setRequestProperty("Content-type", contentType);
            }
            if (serviceHeader != null && !serviceHeader.isEmpty()) {  //传输头消息
                for (Map.Entry<String, String> entry : serviceHeader.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 当outputStr不为null时向输出流写数据
            if (StringUtils.isNotBlank(outputStr)) {
                outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));// 注意编码格式
            }
            int responseCode = conn.getResponseCode();
            if (CodeUtil.HTTP_OK == responseCode) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            result = buffer.toString();
            code = responseCode;
        } catch (Exception e) {
            LOGGER.error("请求异常 requestUrl:{},error:{}", requestUrl, e);
            result = e.toString();
        } finally {
            // 释放资源
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("code", code);
        map.put("result", result);
        return map;
    }

    public static Map<String, Object> httpsRequest(String requestUrl, String method, String contentType, String outputStr, Map<String, String> serviceHeader) {
        Map<String, Object> map = Maps.newHashMap();
        String result;
        HttpsURLConnection conn = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        int code = 402;  //内网返回错误
        try {
            TrustManager[] tm = {new MyX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            //设置超时
            conn.setConnectTimeout(50000);
            conn.setReadTimeout(50000);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(method);
            if (StringUtils.isNotBlank(contentType)) {
                conn.setRequestProperty("Content-type", contentType);
            }
            if (serviceHeader != null && !serviceHeader.isEmpty()) {  //传输头消息
                for (Map.Entry<String, String> entry : serviceHeader.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 当outputStr不为null时向输出流写数据
            if (StringUtils.isNotBlank(outputStr)) {
                outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));// 注意编码格式
            }
            int responseCode = conn.getResponseCode();
            if (CodeUtil.HTTP_OK == responseCode) {
                inputStream = conn.getInputStream();
            } else {
                inputStream = conn.getErrorStream();
            }
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String str;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            result = buffer.toString();
            code = responseCode;
        } catch (Exception e) {
            LOGGER.error("请求异常 requestUrl:{},error:{}", requestUrl, e);
            result = e.toString();
        } finally {
            // 释放资源
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        map.put("code", code);
        map.put("result", result);
        return map;
    }

    // 获得本周一0点时间
    public static Date getTimesWeekmorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    // 获得本周日24点时间
    public static Date getTimesWeeknight() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getTimesWeekmorning());
        cal.add(Calendar.DAY_OF_WEEK, 7);
        return cal.getTime();
    }

    public static void main(String[] args) {
        Date s = getTimesWeekmorning();
        for (int i = 0; i < 7; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(getTimesWeekmorning());
            cal.add(Calendar.DAY_OF_WEEK, i);
            String format = DateUtil.format(cal.getTime(), DateUtil.YMD_DASH);
            System.out.println(format);
        }
    }
}