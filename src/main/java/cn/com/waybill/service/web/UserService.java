package cn.com.waybill.service.web;

import cn.com.waybill.model.User;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface UserService {

    Map<String, Object> login(User user);

    void saveUser(Integer roleId, User user);

    User getUserInfo(String userName);

    void changePassword(String userName, String password, String confirm);

    List<User> getUserList(User user);

    void resetPassword(String uname, String password);
}