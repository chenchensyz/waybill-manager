package cn.com.waybill.tools.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class OrderExcel extends BaseRowModel {

    @ExcelProperty(value = "微信名", index = 0)
    private String userName;

    @ExcelProperty(value = "单号", index = 1)
    private String orderNum;

    @ExcelProperty(value = "顾客姓名", index = 2)
    private String customer;

    @ExcelProperty(value = "顾客手机号", index = 3)
    private String telephone;

    @ExcelProperty(value = "商品明细", index = 4)
    private String detail;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
