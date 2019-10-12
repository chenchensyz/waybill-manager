package cn.com.waybill.config.redis;

import cn.com.waybill.model.User;
import cn.com.waybill.tools.CodeUtil;
import cn.com.waybill.tools.EncryptUtils;
import cn.com.waybill.tools.MessageCode;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@Component
public class RedisTool {

    @Autowired
    private JedisPool jedisPool;

    public Map<String, String> hgetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(CodeUtil.REDIS_DBINDEX);
        Map<String, String> stringMap = Maps.newHashMap();
        try {
            stringMap = jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return stringMap;
    }

    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.select(CodeUtil.REDIS_DBINDEX);
        Long del = 0l;
        try {
            del = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
        return del;
    }

    //创建用户登陆token
    public static String createUserToken(String userName, String timestamp) {
        String localToken = EncryptUtils.MD5Encode(userName + timestamp + CodeUtil.TOKEN_SUFFIX);
        return localToken;
    }

    //获取用户登陆token
    public static String getUserToken(String userName, String token) {
        String key = CodeUtil.REDIS_PREFIX + userName + ":" + token;
        return key;
    }

    //密码加密
    public static String encryptPwd(String userName, String password) {
        String newPwd = EncryptUtils.MD5Encode(userName + password + CodeUtil.PASSWORD_SUFFIX);
        return newPwd;
    }

    //微信登陆token
    public static String createApiToken(String openid, String timestamp) {
        String localToken = EncryptUtils.MD5Encode(openid + timestamp + CodeUtil.API_TOKEN_SUFFIX);
        return localToken;
    }

    //获取用户登陆token
    public static String getApiToken(String openid) {
        String key = CodeUtil.REDIS_PREFIX_WEIXIN + openid;
        return key;
    }

    public User getLocalUser(String key) {
        User user = new User();
        Map<String, String> map = hgetAll(key);
        if (map.isEmpty()) {
            throw new ValueRuntimeException(MessageCode.USERINFO_ERR_SELECT);
        }
        user.setId(getIntValue(map, "userId"));
        user.setUserName(map.get("userName"));
        user.setNickName(map.get("nickName"));
        user.setRoleId(getIntValue(map, "roleId"));
        return user;
    }

    private Integer getIntValue(Map<String, String> map, String key) {
        if (StringUtils.isNotBlank(map.get(key))) {
            return Integer.valueOf(map.get(key));
        }
        return null;
    }
}
