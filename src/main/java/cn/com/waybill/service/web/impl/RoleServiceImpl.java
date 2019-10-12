package cn.com.waybill.service.web.impl;

import cn.com.waybill.dao.RoleMapper;
import cn.com.waybill.model.Role;
import cn.com.waybill.service.web.RoleService;
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
}