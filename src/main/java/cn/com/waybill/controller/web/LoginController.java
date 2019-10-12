package cn.com.waybill.controller.web;

import cn.com.waybill.config.redis.RedisTool;
import cn.com.waybill.model.User;
import cn.com.waybill.service.web.UserService;
import cn.com.waybill.tools.*;
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
@RequestMapping("/login")
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private MessageCodeUtil messageCodeUtil;

    @Autowired
    private UserService userService;


    @Autowired
    private RedisTool redisTool;

    //跳转登录页
    @RequestMapping("toLogin")
    public String toLogin() {
        return "login";
    }

    //登录
    @RequestMapping(method = RequestMethod.POST)
    public RestResponse login(User user) {
        if (StringUtils.isBlank(user.getUserName()) || StringUtils.isBlank(user.getPassword())) {
            return RestResponse.failure("请填写完整登录信息");
        }
        int msgCode = MessageCode.BASE_SUCC_CODE;
        Map<String, Object> map = Maps.newHashMap();
        try {
            map = userService.login(user);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        return RestResponse.res(msgCode, messageCodeUtil.getMessage(msgCode)).setData(map);
    }
}
