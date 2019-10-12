package cn.com.waybill.controller.web;

import cn.com.waybill.config.redis.RedisTool;
import cn.com.waybill.model.User;
import cn.com.waybill.service.web.UserService;
import cn.com.waybill.tools.CodeUtil;
import cn.com.waybill.tools.MessageCode;
import cn.com.waybill.tools.MessageCodeUtil;
import cn.com.waybill.tools.RestResponse;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/buyer")
public class BuyerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuyerController.class);

    @Autowired
    private MessageCodeUtil messageCodeUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTool redisTool;

    //买手列表
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public RestResponse getOrderList(User user, HttpServletRequest request) {
        String userName = request.getHeader("userName");
        String token = request.getHeader("token");
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            User localUser = redisTool.getLocalUser(RedisTool.getUserToken(userName, token));
            if (localUser.getRoleId() > CodeUtil.ROLE_USER) {
                throw new ValueRuntimeException(MessageCode.AUTH_HEADER_VALID_NULL); //用户权限不足
            }
            user.setRoleId(CodeUtil.ROLE_BUYER);
            PageHelper.startPage(user.getPageNum(), user.getPageSize());
            List<User> users = userService.getUserList(user);
            PageInfo<User> userPage = new PageInfo<>(users);
            rest.setData(users).setTotal(userPage.getTotal()).setPage(userPage.getLastPage());
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }

    //买手--保存
    @RequestMapping(value = "saveUser")
    public RestResponse saveUser(User user, HttpServletRequest request) {
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        String userName = request.getHeader("userName");
        String token = request.getHeader("token");
        try {
            User localUser = redisTool.getLocalUser(RedisTool.getUserToken(userName, token));
            if (localUser.getRoleId() > CodeUtil.ROLE_USER) {
                throw new ValueRuntimeException(MessageCode.AUTH_HEADER_VALID_NULL); //用户权限不足
            }
            userService.saveUser(CodeUtil.ROLE_BUYER, user);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }
}
