package cn.com.waybill.interceptor;

import cn.com.waybill.config.redis.RedisTool;
import cn.com.waybill.tools.CodeUtil;
import cn.com.waybill.tools.MessageCode;
import cn.com.waybill.tools.RestResponse;
import cn.com.waybill.tools.SpringUtil;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class ApiInterceptor implements HandlerInterceptor {

    private static Map<Integer, String> messageMap = Maps.newHashMap();

    static {
        messageMap.put(MessageCode.AUTH_HEADER_USER_NULL, "头信息中缺少 用户 信息");
        messageMap.put(MessageCode.AUTH_HEADER_TOKEN_NULL, "头信息中缺少 token 信息");
        messageMap.put(MessageCode.AUTH_HEADER_TIMESTAMP_FILED, "头信息中缺少 TIMESTAMP 信息");
        messageMap.put(MessageCode.AUTH_HEADER_TOKEN_FILED, "token验证失败");
        messageMap.put(MessageCode.AUTH_HEADER_TOKEN_TIMEOUT, "token已失效");
        messageMap.put(MessageCode.AUTH_HEADER_USER_FILED, "用户验证失败");
        messageMap.put(MessageCode.AUTH_HEADER_TIMESTAMP_ERR, "TIMESTAMP信息不一致");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(MyInteceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        String openid = request.getHeader("openid");
        String timestamp = request.getHeader("timestamp");
        if (StringUtils.isBlank(token)) {
            writeJsonResult(response, 401, MessageCode.AUTH_HEADER_TOKEN_NULL);
            return false;
        }
        if (StringUtils.isBlank(openid)) {
            writeJsonResult(response, 401, MessageCode.AUTH_HEADER_USER_NULL);
            return false;
        }

        String localToken = RedisTool.createApiToken(openid, timestamp);
        if (!token.equals(localToken)) {
            writeJsonResult(response, 401, MessageCode.AUTH_HEADER_TOKEN_FILED);
            return false;
        }

        JedisPool jedisPool = SpringUtil.getBean(JedisPool.class);
        Jedis jedis = jedisPool.getResource();
        jedis.select(CodeUtil.REDIS_DBINDEX);
        try {
            Map<String, String> tokenMap = jedis.hgetAll(RedisTool.getApiToken(openid));
            if (tokenMap.isEmpty()) {
                writeJsonResult(response, 401, MessageCode.AUTH_HEADER_TOKEN_TIMEOUT);
                return false;
            }
            if (!tokenMap.get("timestamp").equals(timestamp)) {
                writeJsonResult(response, 401, MessageCode.AUTH_HEADER_TIMESTAMP_ERR);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_LOGIN); //用户登陆失败
        } finally {
            jedis.close();
        }
        return true;
    }

    private void writeJsonResult(HttpServletResponse response, int httpCode, int code) {
        PrintWriter out = null;
        try {
            response.setStatus(httpCode);
            response.setContentType(CodeUtil.CONTEXT_JSON);
            response.setCharacterEncoding(CodeUtil.cs.toString());
            out = response.getWriter();
            out.print(RestResponse.res(code, messageMap.get(code)));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
