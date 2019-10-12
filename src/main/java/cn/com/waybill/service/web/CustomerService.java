package cn.com.waybill.service.web;

import cn.com.waybill.model.Customer;
import cn.com.waybill.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CustomerService {

    List<Customer> getCustomerList(Customer customer);

    void saveCustomer(Customer customer);

    void delCustomer(List<Integer> ids);

    void addCustomerMore(User localUser, HttpServletRequest request);

}