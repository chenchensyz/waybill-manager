package cn.com.waybill.model;

import cn.com.waybill.model.common.BaseEntity;
import cn.com.waybill.tools.DateUtil;

import java.util.Date;

public class User extends BaseEntity {
    private Integer id;

    private String userName;

    private String password;

    private String nickName;

    private Integer state;

    private Integer roleId;

    private Long expire;

    private Date createTime;

    private Date updateTime;

    private String createTimeStr;

    private String updateTimeStr;

    private String expireStr;

    private String telephone;

    private String remark;

    private String roleName;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Long getExpire() {
        return expire;
    }

    public void setExpire(Long expire) {
        this.expire = expire;
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

    public String getExpireStr() {
        return expire == null ? "" : DateUtil.format(new Date(expire), DateUtil.YMD_DASH_WITH_TIME);
    }

    public void setExpireStr(String expireStr) {
        this.expireStr = expireStr;
    }

    public String getUpdateTimeStr() {
        return updateTime == null ? "" : DateUtil.format(updateTime, DateUtil.YMD_DASH_WITH_TIME);
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}