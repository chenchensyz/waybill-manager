package cn.com.waybill.service.web;

import cn.com.waybill.model.common.PermModel;
import cn.com.waybill.model.Permission;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface PermissionService {

    List<PermModel> getUserPerms(Integer roleId);

    List<Permission> getUserPermIds(Integer roleId);

    void saveUserPerms(JSONObject jsonParam);
}