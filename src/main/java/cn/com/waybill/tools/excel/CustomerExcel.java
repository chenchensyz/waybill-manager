package cn.com.waybill.tools.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class CustomerExcel extends BaseRowModel {

    @ExcelProperty(value = "名称", index = 0)
    private String userName;

    @ExcelProperty(value = "手机号", index = 1)
    private String telephone;

    @ExcelProperty(value = "备注", index = 2)
    private String remark;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
}
