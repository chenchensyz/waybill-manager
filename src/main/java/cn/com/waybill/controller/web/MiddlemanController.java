package cn.com.waybill.controller.web;

import cn.com.waybill.config.redis.RedisTool;
import cn.com.waybill.model.Customer;
import cn.com.waybill.model.User;
import cn.com.waybill.service.web.CustomerService;
import cn.com.waybill.tools.*;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/middleman")
public class MiddlemanController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiddlemanController.class);

    @Autowired
    private MessageCodeUtil messageCodeUtil;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private RedisTool redisTool;

    //中间商列表
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public RestResponse getOrderList(Customer customer, HttpServletRequest request) {
        String userName = request.getHeader("userName");
        String token = request.getHeader("token");
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            User localUser = redisTool.getLocalUser(RedisTool.getUserToken(userName, token));
            customer.setCreator(localUser.getId());
            customer.setType(CodeUtil.USER_MIDDLEMAN);
            PageHelper.startPage(customer.getPageNum(), customer.getPageSize());
            List<Customer> customers = customerService.getCustomerList(customer);
            PageInfo<Customer> customerPage = new PageInfo<>(customers);
            rest.setData(customers).setTotal(customerPage.getTotal()).setPage(customerPage.getLastPage());
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }

    //中间商--保存
    @RequestMapping(value = "saveCustomer", method = RequestMethod.POST)
    public RestResponse saveOrder(Customer customer, HttpServletRequest request) {
        String userName = request.getHeader("userName");
        String token = request.getHeader("token");
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            User localUser = redisTool.getLocalUser(RedisTool.getUserToken(userName, token));
            customer.setCreator(localUser.getId());
            customerService.saveCustomer(customer);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }

    //中间商--删除
    @RequestMapping(value = "delCustomer")
    public RestResponse delOrder(@RequestBody JSONObject jsonParam) {
        List<Integer> ids = (List<Integer>) jsonParam.get("ids");
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            customerService.delCustomer(ids);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }

    @RequestMapping(value = "addCustomerMore", method = RequestMethod.POST)
    public RestResponse addCustomerMore(HttpServletRequest request) {
        String userName = request.getHeader("userName");
        String token = request.getHeader("token");
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            User localUser = redisTool.getLocalUser(RedisTool.getUserToken(userName, token));
            customerService.addCustomerMore(localUser, request);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }
}
