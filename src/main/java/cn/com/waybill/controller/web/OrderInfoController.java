package cn.com.waybill.controller.web;

import cn.com.waybill.config.redis.RedisTool;
import cn.com.waybill.model.OrderInfo;
import cn.com.waybill.model.User;
import cn.com.waybill.service.web.OrderInfoService;
import cn.com.waybill.tools.*;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderInfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderInfoController.class);

    @Autowired
    private MessageCodeUtil messageCodeUtil;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private RedisTool redisTool;

    //订单列表
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public RestResponse getOrderList(OrderInfo orderInfo, HttpServletRequest request) {
        String userName = request.getHeader("userName");
        String token = request.getHeader("token");
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            User localUser = redisTool.getLocalUser(RedisTool.getUserToken(userName, token));
            orderInfo.setCreator(localUser.getId());
            PageHelper.startPage(orderInfo.getPageNum(), orderInfo.getPageSize());
            List<OrderInfo> orderInfos = orderInfoService.getOrderList(orderInfo);
            PageInfo<OrderInfo> orderInfoPage = new PageInfo<>(orderInfos);
            rest.setData(orderInfos).setTotal(orderInfoPage.getTotal()).setPage(orderInfoPage.getLastPage());
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }

    //订单--保存
    @RequestMapping(value = "saveOrder", method = RequestMethod.POST)
    public RestResponse saveOrder(OrderInfo orderInfo, HttpServletRequest request) {
        String userName = request.getHeader("userName");
        String token = request.getHeader("token");
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            User localUser = redisTool.getLocalUser(RedisTool.getUserToken(userName, token));
            orderInfo.setCreator(localUser.getId());
            orderInfoService.saveOrder(orderInfo);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }

    //订单--批量增加
    @RequestMapping(value = "addOrderMore", method = RequestMethod.POST)
    public RestResponse addOrderMore(HttpServletRequest request) {
        String userName = request.getHeader("userName");
        String token = request.getHeader("token");
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            User localUser = redisTool.getLocalUser(RedisTool.getUserToken(userName, token));
            orderInfoService.addOrderMore(localUser, request);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }

    //删除订单
    @RequestMapping(value = "delOrder")
    public RestResponse delOrder(@RequestBody JSONObject jsonParam) {
        List<Integer> ids = (List<Integer>) jsonParam.get("ids");
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            orderInfoService.updateOrderState(ids);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }

        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }
}
