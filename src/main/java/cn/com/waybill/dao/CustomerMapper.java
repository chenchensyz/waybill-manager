package cn.com.waybill.dao;

import cn.com.waybill.model.Customer;

import java.util.List;

public interface CustomerMapper {

    List<Customer> getCustomerList(Customer customer);

    int insertCustomer(Customer customer);

    int updateCustomer(Customer customer);

    int insertCustomerMore(List<Customer> customers);

    int delCustomerInIds(List<Integer> ids);

    Customer getCustomerByOpenId(String telephone);
}