package cn.com.waybill.model.common;

import cn.com.waybill.tools.*;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.google.common.collect.Maps;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
