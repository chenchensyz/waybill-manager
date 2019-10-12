package cn.com.waybill.controller.web;

import cn.com.waybill.config.redis.RedisTool;
import cn.com.waybill.model.common.PermModel;
import cn.com.waybill.model.Permission;
import cn.com.waybill.model.User;
import cn.com.waybill.service.web.PermissionService;
import cn.com.waybill.tools.MessageCode;
import cn.com.waybill.tools.MessageCodeUtil;
import cn.com.waybill.tools.RestResponse;
import cn.com.waybill.tools.exception.ValueRuntimeException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private MessageCodeUtil messageCodeUtil;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RedisTool redisTool;

    //用户权限
    @RequestMapping(value = "userPerms")
    public RestResponse userPerms(HttpServletRequest request) {
        String userName = request.getHeader("userName");
        String token = request.getHeader("token");
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            User localUser = redisTool.getLocalUser(RedisTool.getUserToken(userName, token));
            List<PermModel> perms = permissionService.getUserPerms(localUser.getRoleId());
            rest.setData(perms);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }

    //用户权限
    @RequestMapping(value = "permsSet")
    public RestResponse permsSet(HttpServletRequest request, Integer roleId) {
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            List<Permission> perms = permissionService.getUserPermIds(roleId);
            rest.setData(perms);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }

    //用户权限--保存
    @RequestMapping(value = "userPermsSave")
    public RestResponse userPermsSave(@RequestBody JSONObject jsonParam) {
        RestResponse rest = new RestResponse();
        int msgCode = MessageCode.BASE_SUCC_CODE;
        try {
            permissionService.saveUserPerms(jsonParam);
        } catch (ValueRuntimeException e) {
            msgCode = (int) e.getValue();
        }
        rest.setCode(msgCode);
        rest.setMessage(messageCodeUtil.getMessage(msgCode));
        return rest;
    }
}
