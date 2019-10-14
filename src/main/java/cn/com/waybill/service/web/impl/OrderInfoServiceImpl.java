package cn.com.waybill.service.web.impl;

import cn.com.waybill.dao.OrderInfoMapper;
import cn.com.waybill.model.OrderInfo;
import cn.com.waybill.model.User;
import cn.com.waybill.service.web.OrderInfoService;
import cn.com.waybill.tools.MessageCode;
import cn.com.waybill.tools.excel.ExcelUtil;
import cn.com.waybill.tools.excel.OrderExcel;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderInfoServiceImpl.class);

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public List<OrderInfo> getOrderList(OrderInfo orderInfo) {
        return orderInfoMapper.getOrderList(orderInfo);
    }

    @Override
    public void saveOrder(OrderInfo orderInfo) {
        int count = 0;
        if (orderInfo.getId() == null) {
            orderInfo.setCreateTime(new Date());
            count = orderInfoMapper.insertOrder(orderInfo);
        } else {
            orderInfo.setUpdateTime(new Date());
            count = orderInfoMapper.updateOrder(orderInfo);
        }
        if (count == 0) {
            throw new ValueRuntimeException(MessageCode.ORDER_ERR_SAVE);
        }
    }

    @Override
    public void updateOrderState(List<Integer> ids) {
        int count = orderInfoMapper.updateOrderState(ids);
        if (count != ids.size()) {
            throw new ValueRuntimeException(MessageCode.ORDER_ERR_DEL);
        }
    }

    @Override
    @Transactional
    public void addOrderMore(User localUser, HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = files.get(0);
        List<Object> datas = ExcelUtil.readExcel(file, new OrderExcel());
        if (datas == null || datas.size() == 0) {
            throw new ValueRuntimeException(MessageCode.BASE_FILE_NULL);
        }
        List<OrderInfo> orderInfos = Lists.newLinkedList();
        for (Object o : datas) {
            OrderExcel orderExcel = (OrderExcel) o;
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setUserName(orderExcel.getUserName());
            orderInfo.setOrderNum(orderExcel.getOrderNum());
            orderInfo.setCustomer(orderExcel.getCustomer());
            orderInfo.setTelephone(orderExcel.getTelephone());
            orderInfo.setDetail(orderExcel.getDetail());
            orderInfo.setCreator(localUser.getId());
            orderInfos.add(orderInfo);
        }
        int count = orderInfoMapper.insertOrderMore(orderInfos);
        if (count != orderInfos.size()) {
            throw new ValueRuntimeException(MessageCode.ORDER_ERR_SAVE);
        }
    }

    @Override
    public OrderInfo getOrderInfo(Integer id) {
        OrderInfo orderInfo = orderInfoMapper.getOrderInfo(id);
        if (orderInfo == null) {

        }
        return null;
    }
}