package cn.com.waybill.service.web.impl;

import cn.com.waybill.dao.CustomerMapper;
import cn.com.waybill.model.Customer;
import cn.com.waybill.model.User;
import cn.com.waybill.service.web.CustomerService;
import cn.com.waybill.tools.CodeUtil;
import cn.com.waybill.tools.MessageCode;
import cn.com.waybill.tools.excel.CustomerExcel;
import cn.com.waybill.tools.excel.ExcelUtil;
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
import java.util.List;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public List<Customer> getCustomerList(Customer customer) {
        return customerMapper.getCustomerList(customer);
    }

    @Override
    public void saveCustomer(Customer customer) {
        int count = 0;
        customer.setType(CodeUtil.USER_MIDDLEMAN);
        if (customer.getId() == null) {
            count = customerMapper.insertCustomer(customer);
        } else {
            count = customerMapper.updateCustomer(customer);
        }
        if (count == 0) {
            throw new ValueRuntimeException(MessageCode.ORDER_ERR_SAVE);
        }
    }

    @Override
    @Transactional
    public void addCustomerMore(User localUser, HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = files.get(0);
        List<Object> datas = ExcelUtil.readExcel(file, new CustomerExcel());
        if (datas == null || datas.size() == 0) {
            throw new ValueRuntimeException(MessageCode.BASE_FILE_NULL);
        }
        List<Customer> customers = Lists.newLinkedList();
        for (Object o : datas) {
            CustomerExcel customerExcel = (CustomerExcel) o;
            Customer customer = new Customer();
            customer.setUserName(customerExcel.getUserName());
            customer.setTelephone(customerExcel.getTelephone());
            customer.setRemark(customerExcel.getRemark());
            customer.setType(CodeUtil.USER_MIDDLEMAN);
            customer.setCreator(localUser.getId());
            customers.add(customer);
        }
        int count = customerMapper.insertCustomerMore(customers);
        if (count != customers.size()) {
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_SAVE);
        }
    }

    @Override
    public void delCustomer(List<Integer> ids) {
        int count = customerMapper.delCustomerInIds(ids);
        if (count != ids.size()) {
            throw new ValueRuntimeException(MessageCode.ORDER_ERR_SAVE);
        }

    }
}