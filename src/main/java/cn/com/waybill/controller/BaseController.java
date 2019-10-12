package cn.com.waybill.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Sets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

public class BaseController {

    protected Object filterParam(Object o, String filters) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        Set<String> userSet = Sets.newHashSet(Arrays.asList(filters.split(",")));
        filter.getExcludes().addAll(userSet);
        String jsonString = JSONObject.toJSONString(o, filter);
        return JSONObject.parse(jsonString);
    }


    //bean转换map
    public static Map transBean2Map(Object javaBean, List<String> excloude) {
        Map map = new HashMap();
        try {
            // 获取javaBean属性
            BeanInfo beanInfo = Introspector.getBeanInfo(javaBean.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            if (propertyDescriptors != null && propertyDescriptors.length > 0) {
                String propertyName = null; // javaBean属性名
                Object propertyValue = null; // javaBean属性值
                for (PropertyDescriptor pd : propertyDescriptors) {
                    propertyName = pd.getName();
                    if (excloude == null || (excloude != null && !excloude.contains(propertyName))) {
                        if (!propertyName.equals("class")) {
                            Method readMethod = pd.getReadMethod();
                            propertyValue = readMethod.invoke(javaBean, new Object[0]);
                            map.put(propertyName, propertyValue);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    //将图片输出至网页
    public static void setResponseFile(HttpServletResponse response, byte[] defalutImg,
                                       String contentType) {
        try {
            if (defalutImg == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            response.setContentType(contentType);
            response.setCharacterEncoding("UTF-8");
            if (defalutImg == null) {
                response.setContentLength(defalutImg.length);
                response.getOutputStream().write(defalutImg, 0, defalutImg.length);
                return;
            }

            response.setContentLength(defalutImg.length);

            OutputStream opStream = response.getOutputStream();
            InputStream inStream = new ByteArrayInputStream(defalutImg);

            int dataLen = 4 * 1024;
            byte[] data = new byte[dataLen];

            int len;
            while ((len = inStream.read(data, 0, dataLen)) != -1) {
                opStream.write(data, 0, len);
            }
            opStream.close();
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 以text格式输出
     *
     * @param response
     */
    public void setResponseText(HttpServletResponse response, String responseObject) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/plain; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseObject);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * 以JSON格式输出
     *
     * @param response
     */
    public void setResponseJson(HttpServletResponse response, String responseObject) {
        //将实体对象转换为JSON Object转换
        JSONObject responseJSONObject = JSONObject.parseObject(responseObject);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.append(responseJSONObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
