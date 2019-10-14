package cn.com.waybill.model;

import cn.com.waybill.model.common.BaseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderInfo extends BaseEntity {
    private Integer id;

    private String userName;

    private String orderNum;

    private String customer;

    private String telephone;

    private String detail;

    private Integer creator;

    private Integer state;

    private Date createTime;

    private Date updateTime;

    private Long createLongTime;

    private Long updateLongTime;

    private String shipperCode;

    private Object Traces;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer == null ? null : customer.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateLongTime() {
        return createTime == null ? null : createTime.getTime();
    }

    public void setCreateLongTime(Long createLongTime) {
        this.createLongTime = createLongTime;
    }

    public Long getUpdateLongTime() {
        return updateTime == null ? null : updateTime.getTime();
    }

    public void setUpdateLongTime(Long updateLongTime) {
        this.updateLongTime = updateLongTime;
    }

    public String getShipperCode() {
        return shipperCode;
    }

    public void setShipperCode(String shipperCode) {
        this.shipperCode = shipperCode;
    }

    public Object getTraces() {
        return Traces;
    }

    public void setTraces(Object traces) {
        Traces = traces;
    }
}