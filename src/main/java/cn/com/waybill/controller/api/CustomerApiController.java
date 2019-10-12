package cn.com.waybill.controller.api;

import cn.com.waybill.config.redis.RedisTool;
import cn.com.waybill.service.api.CustomerApiService;
import cn.com.waybill.tools.MessageCode;
import cn.com.waybill.tools.MessageCodeUtil;
import cn.com.waybill.tools.RestResponse;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApiController.class);

    @Autowired
    private MessageCodeUtil messageCodeUtil;

    @Autowired
    private CustomerApiService customerApiService;

    @Autowired
    private RedisTool redisTool;

    //登录
    @RequestMapping("login")
    public RestResponse login(String telephone, String jsCode) {
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            rest = customerApiService.login(rest, telephone, jsCode);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        if (rest.get("message") == null) {
            rest.setMessage(messageCodeUtil.getMessage(msgCode));
        }
        return rest;
    }
}
