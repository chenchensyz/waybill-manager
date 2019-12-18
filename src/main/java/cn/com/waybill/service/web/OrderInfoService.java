package cn.com.waybill.service.web;

import cn.com.waybill.model.OrderInfo;
import cn.com.waybill.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderInfoService {

    List<OrderInfo> getOrderList(OrderInfo orderInfo);

    void saveOrder(OrderInfo orderInfo);

    void addOrderMore(User localUser, HttpServletRequest request);

    void updateOrderState(List<Integer> ids);

    OrderInfo getOrderInfo(Integer id);

    List<OrderInfo> getApiOrderList(OrderInfo orderInfo);

}