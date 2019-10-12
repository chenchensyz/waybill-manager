package cn.com.waybill.dao;

import cn.com.waybill.dao.common.BaseDao;
import cn.com.waybill.model.User;

import java.util.List;

public interface UserMapper extends BaseDao<User> {

    List<User> getUserList(User user);

    User getUserByUserName(String userName);

    int insertUser(User user);

    int updateUser(User user);
}