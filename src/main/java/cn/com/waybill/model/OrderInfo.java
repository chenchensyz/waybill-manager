package cn.com.waybill.model;

import cn.com.waybill.model.common.BaseEntity;
import cn.com.waybill.tools.DateUtil;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

public class OrderInfo extends BaseEntity implements Serializable {
    private Integer id;

    private String userName;

    private String orderNum;

    private String customer;

    private String telephone;

    private String detail;

    private Integer creator;

    private Integer state;

    private Date selectTime;

    private Date createTime;

    private Date updateTime;

    private String createTimeStr;

    private Long updateLongTime;

    private String shipperCode;

    private String shipperName;

    private String shipperBody;

    private Object Traces;

    private String website;//网址

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

    public String getCreateTimeStr() {
        return createTime == null ? "" : DateUtil.format(createTime, DateUtil.YMD_DASH_WITH_TIME);
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
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

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    @JSONField(serialize = false)
    public String getShipperBody() {
        return shipperBody;
    }

    public void setShipperBody(String shipperBody) {
        this.shipperBody = shipperBody;
    }

    public Date getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(Date selectTime) {
        this.selectTime = selectTime;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}