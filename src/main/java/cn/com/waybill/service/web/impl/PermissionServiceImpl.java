package cn.com.waybill.service.web.impl;

import cn.com.waybill.dao.PermissionMapper;
import cn.com.waybill.model.common.PermModel;
import cn.com.waybill.model.Permission;
import cn.com.waybill.service.web.PermissionService;
import cn.com.waybill.tools.MessageCode;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public List<PermModel> getUserPerms(Integer roleId) {
        List<Permission> userPerms = permissionMapper.getUserPerms(roleId);
        List<PermModel> modelList = Lists.newLinkedList();
        for (Permission perm : userPerms) {
            if (perm.getParentId() == 0) {
                PermModel model = new PermModel();
                model.setId(perm.getId());
                model.setIcon(perm.getIcon());
                model.setIndex(perm.getUrl());
                model.setTitle(perm.getName());
                List<PermModel> subList = Lists.newLinkedList();
                for (Permission sub : userPerms) {
                    if (sub.getParentId().equals(perm.getId())) {
                        PermModel subModel = new PermModel();
                        subModel.setId(sub.getId());
                        subModel.setIcon(sub.getIcon());
                        subModel.setIndex(sub.getUrl());
                        subModel.setTitle(sub.getName());
                        subList.add(subModel);
                    }
                }
                if (subList.size() > 0) {
                    model.setSubs(subList);
                }
                modelList.add(model);
            }
        }
        return modelList;
    }

    @Override
    public List<Permission> getUserPermIds(Integer roleId) {
        return permissionMapper.getUserPerms(roleId);
    }

    @Override
    @Transactional
    public void saveUserPerms(JSONObject jsonParam) {
        Integer roleId = jsonParam.getInteger("roleId");
        List<Integer> perms = (List<Integer>) jsonParam.get("perms");
        permissionMapper.deletePermByRoleId(roleId);
        int count2 = permissionMapper.insertUserPerms(roleId, perms);
        if (count2 == 0) {
            throw new ValueRuntimeException(MessageCode.PERM_ERR_SAVE);
        }
    }
}