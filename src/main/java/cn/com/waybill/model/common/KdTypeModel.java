package cn.com.waybill.model.common;

import java.util.List;

public class KdTypeModel {

    private String EBusinessID;
    private String LogisticCode;
    private Boolean Success;
    private Integer Code;
    private List<KdCodeModel> Shippers;

    public String getEBusinessID() {
        return EBusinessID;
    }

    public void setEBusinessID(String EBusinessID) {
        this.EBusinessID = EBusinessID;
    }

    public String getLogisticCode() {
        return LogisticCode;
    }

    public void setLogisticCode(String logisticCode) {
        LogisticCode = logisticCode;
    }

    public Boolean getSuccess() {
        return Success;
    }

    public void setSuccess(Boolean success) {
        Success = success;
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public List<KdCodeModel> getShippers() {
        return Shippers;
    }

    public void setShippers(List<KdCodeModel> shippers) {
        Shippers = shippers;
    }
}
