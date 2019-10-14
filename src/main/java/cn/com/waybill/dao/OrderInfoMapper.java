package cn.com.waybill.dao;

import cn.com.waybill.dao.common.BaseDao;
import cn.com.waybill.model.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderInfoMapper {

    List<OrderInfo> getOrderList(OrderInfo orderInfo);

    int insertOrder(OrderInfo orderInfo);

    int updateOrder(OrderInfo orderInfo);

    int deleteOrderById(Integer orderId);

    int updateOrderState(List<Integer> ids);

    int insertOrderMore(List<OrderInfo> orderInfos);

    OrderInfo getOrderInfo(Integer id);
}