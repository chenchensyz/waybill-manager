package cn.com.waybill.service.api.impl;

import cn.com.waybill.config.redis.RedisTool;
import cn.com.waybill.dao.CustomerMapper;
import cn.com.waybill.model.Customer;
import cn.com.waybill.model.common.WxCodeModel;
import cn.com.waybill.service.api.CustomerApiService;
import cn.com.waybill.tools.*;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@Service("customerApiService")
public class CustomerApiServiceImpl implements CustomerApiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApiServiceImpl.class);

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private MessageCodeUtil messageCodeUtil;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public RestResponse login(RestResponse rest, String telephone, String jsCode) {
        String url = messageCodeUtil.getMessage(CodeUtil.WX_PREFIX + CodeUtil.WX_CODE2SESSION);
        String appid = messageCodeUtil.getMessage(CodeUtil.WX_PREFIX + CodeUtil.WX_APPID);
        String secret = messageCodeUtil.getMessage(CodeUtil.WX_PREFIX + CodeUtil.WX_SECRET);
        url = url.replace(CodeUtil.WX_APPID, appid);
        url = url.replace(CodeUtil.WX_SECRET, secret);
        url = url.replace(CodeUtil.WX_JSCODE, jsCode);
        WxCodeModel codeModel = null;
        Map<String, Object> resultMap = HttpConnection.httpsRequest(url, CodeUtil.METHOD_GET, null, null, null);
        if (resultMap.get("code") != null) {
            if (CodeUtil.HTTP_OK != (Integer) resultMap.get("code")) {
                throw new ValueRuntimeException(MessageCode.WX_CONNECT_ERR);
            }
            codeModel = JSONObject.parseObject(resultMap.get("result").toString(), WxCodeModel.class);
            if (codeModel != null && codeModel.getErrcode() != null) {  //微信验证失败
                rest.setMessage(codeModel.getErrmsg());
                throw new ValueRuntimeException(MessageCode.WX_VALID_ERR);
            }
        }
//        WxCodeModel codeModel = new WxCodeModel();
//        codeModel.setOpenid("asdfggfsds");
        Customer customer = customerMapper.getCustomerByOpenId(codeModel.getOpenid());
        int count;
        if (customer == null) {
            customer = new Customer();
            customer.setOpenId(codeModel.getOpenid());
            customer.setType(CodeUtil.USER_CUSTOMER);
            count = customerMapper.insertCustomer(customer);
            if (count == 0) {
                throw new ValueRuntimeException(MessageCode.USERINFO_ERR_SAVE);
            }
        }
//        else {
//            if (StringUtils.isBlank(customer.getOpenId())) {
//                customer.setOpenId(codeModel.getOpenid());
//                count = customerMapper.updateCustomer(customer);
//                if (count == 0) {
//                    throw new ValueRuntimeException(MessageCode.USERINFO_ERR_SAVE);
//                }
//            }
//        }

        long millis = System.currentTimeMillis();
        //生成token
        String token = RedisTool.createApiToken(customer.getOpenId(), millis + "");
        Jedis jedis = jedisPool.getResource();
        jedis.select(CodeUtil.REDIS_DBINDEX);
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("openid", customer.getOpenId());
            map.put("timestamp", millis + "");
            String key = RedisTool.getApiToken(customer.getOpenId());
            jedis.hmset(key, map);

            map.put("token", token);
            rest.setData(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_LOGIN); //用户登陆失败
        } finally {
            jedis.close();
        }
        return rest;
    }
}