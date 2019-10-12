package cn.com.waybill.service.api;

import cn.com.waybill.tools.RestResponse;

import java.util.Map;

public interface CustomerApiService {

    RestResponse login(RestResponse rest,String telephone, String jsCode);


}