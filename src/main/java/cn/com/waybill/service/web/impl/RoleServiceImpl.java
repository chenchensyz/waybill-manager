package cn.com.waybill.service.web.impl;

import cn.com.waybill.config.redis.RedisTool;
import cn.com.waybill.dao.RoleMapper;
import cn.com.waybill.model.Role;
import cn.com.waybill.model.User;
import cn.com.waybill.service.web.RoleService;
import cn.com.waybill.tools.CodeUtil;
import cn.com.waybill.tools.MessageCode;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public List<Role> getRoleList(Role role) {
        return roleMapper.getRoleList(role);
    }

    @Override
    public void saveRole(Role role) {
        int count;
        if (role.getId()== null) {
            count = roleMapper.insertRole(role);
        } else {
            count = roleMapper.updateRole(role);
        }
        if (count == 0) {
            throw new ValueRuntimeException(MessageCode.ROLE_ERR_SAVE);
        }
    }
}