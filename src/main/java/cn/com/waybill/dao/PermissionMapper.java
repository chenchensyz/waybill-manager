package cn.com.waybill.dao;

import cn.com.waybill.dao.common.BaseDao;
import cn.com.waybill.model.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {

    List<Permission> getUserPerms(Integer roleId);

    int deletePermByRoleId(Integer roleId);

    int insertUserPerms(@Param("roleId") Integer roleId, @Param("perms") List<Integer> perms);
}