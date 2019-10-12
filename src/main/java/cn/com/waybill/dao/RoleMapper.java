package cn.com.waybill.dao;

import cn.com.waybill.dao.common.BaseDao;
import cn.com.waybill.model.Role;

import java.util.List;

public interface RoleMapper{

    List<Role> getRoleList(Role role);
}