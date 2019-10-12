package cn.com.waybill.service.web.impl;

import cn.com.waybill.config.redis.RedisTool;
import cn.com.waybill.dao.UserMapper;
import cn.com.waybill.model.User;
import cn.com.waybill.service.web.UserService;
import cn.com.waybill.tools.CodeUtil;
import cn.com.waybill.tools.EncryptUtils;
import cn.com.waybill.tools.MessageCode;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public List<User> getUserList(User user) {
        return userMapper.getUserList(user);
    }

    @Override
    public Map<String, Object> login(User user) {
        User userSec = userMapper.getUserByUserName(user.getUserName());
        Map<String, Object> resultMap = Maps.newHashMap();
        if (userSec == null) {
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_SELECT); //用户不存在
        }

        String newPwd = EncryptUtils.MD5Encode(user.getUserName() + user.getPassword() + CodeUtil.PASSWORD_SUFFIX);
        if (!newPwd.equals(userSec.getPassword())) {
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_PASSWORD); //
        }

        if (userSec.getState() == 0) {
            throw new ValueRuntimeException(MessageCode.USERINFO_DISABLE); //用户已被禁用
        }

        long millis = System.currentTimeMillis();
        //生成token
        String token = RedisTool.createUserToken(user.getUserName(), millis + "");
        Jedis jedis = jedisPool.getResource();
        jedis.select(CodeUtil.REDIS_DBINDEX);
        try {
            Map<String, String> map = Maps.newHashMap();
            map.put("userId", userSec.getId() + "");
            map.put("userName", userSec.getUserName());
            map.put("nickName", userSec.getNickName());
            map.put("roleId", userSec.getRoleId() + "");
            map.put("timestamp", millis + "");
            String key = RedisTool.getUserToken(user.getUserName(), token);
            jedis.hmset(key, map);
            jedis.expire(key, CodeUtil.REDIS_EXPIRE_TIME);

            resultMap.putAll(map);
            resultMap.put("token", token);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_LOGIN); //用户登陆失败
        } finally {
            jedis.close();
        }
        return resultMap;
    }

    @Override
    public void saveUser(Integer roleId, User user) {
        User userSec = userMapper.getUserByUserName(user.getUserName());
        if (userSec != null) {
            throw new ValueRuntimeException(MessageCode.USERINFO_EXIST); //已存在
        }
        int count;
        if (user.getId() == null) {
            user.setState(1);
            user.setRoleId(roleId);
            user.setPassword(RedisTool.encryptPwd(user.getUserName(), CodeUtil.DEFAULT_PASSWORD));
            count = userMapper.insertUser(user);
        } else {
            count = userMapper.updateUser(user);
        }
        if (count == 0) {
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_SAVE);
        }
    }

    @Override
    public User getUserInfo(String userName) {
        User user = userMapper.getUserByUserName(userName);
        if (user == null) {
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_SELECT); //用户不存在
        }
        return user;
    }

    @Override
    public void changePassword(String userName, String password, String confirm) {
        User user = userMapper.getUserByUserName(userName);
        String secPwd = RedisTool.encryptPwd(userName, password);
        if (!user.getPassword().equals(secPwd)) {
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_OLDPASS); //原密码错误
        }
        String newPwd = RedisTool.encryptPwd(userName, confirm);
        user.setPassword(newPwd);
        int count = userMapper.updateUser(user);
        if (count == 0) {
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_RESETPASS); //修改密码失败
        }
        Jedis jedis = jedisPool.getResource();
        jedis.select(CodeUtil.REDIS_DBINDEX);
        try {
            Set<String> keys = jedis.keys(CodeUtil.REDIS_PREFIX + userName + ":*");
            for (String key : keys) {
                jedis.del(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }

    @Override
    public void resetPassword(String uname, String password) {
        if (StringUtils.isBlank(password)) {
            password = CodeUtil.DEFAULT_PASSWORD;
        }
        User user = userMapper.getUserByUserName(uname);
        if (user == null) {
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_SELECT); //用户不存在
        }
        user.setPassword(RedisTool.encryptPwd(uname, password));
        int count = userMapper.updateUser(user);
        if (count == 0) {
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_RESETPASS); //修改密码失败
        }
    }
}