package cn.com.waybill.controller.api;

import cn.com.waybill.config.redis.RedisTool;
import cn.com.waybill.model.OrderInfo;
import cn.com.waybill.service.web.OrderInfoService;
import cn.com.waybill.tools.MessageCode;
import cn.com.waybill.tools.MessageCodeUtil;
import cn.com.waybill.tools.RestResponse;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import cn.com.waybill.tools.filter.FilterParamUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderApiController.class);

    @Autowired
    private MessageCodeUtil messageCodeUtil;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private RedisTool redisTool;

    //订单列表
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public RestResponse getOrderList(OrderInfo orderInfo) {
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            if (StringUtils.isBlank(orderInfo.getTelephone())) {
                throw new ValueRuntimeException(MessageCode.ORDER_SEC_PHONE);
            }
            PageHelper.startPage(orderInfo.getPageNum(), orderInfo.getPageSize());
            List<OrderInfo> orderInfos = orderInfoService.getOrderList(orderInfo);
            PageInfo<OrderInfo> orderInfoPage = new PageInfo<>(orderInfos);
            Object orderInfosFilter = FilterParamUtil.filterParam(orderInfos, FilterParamUtil.API_ORDER_LIST);
            rest.setData(orderInfosFilter).setTotal(orderInfoPage.getTotal()).setPage(orderInfoPage.getLastPage());
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }

    //订单详情
    @RequestMapping(value = "info")
    public RestResponse getOrderInfo(Integer id) {
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            OrderInfo orderInfo = orderInfoService.getOrderInfo(id);
            Object orderInfoFilter = FilterParamUtil.filterParam(orderInfo, FilterParamUtil.API_ORDER_LIST);
            rest.setData(orderInfoFilter);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }
}
