package cn.com.waybill.tools;

import java.util.HashMap;

/**
 * ResponseBody构造器。一般用于ajax、rest等类型的Web服务
 */
public class RestResponse extends HashMap<String, Object> {
    public static RestResponse success(){
        return success("操作成功");
    }
    public static RestResponse success(String message){
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(0);
        restResponse.setMessage(message);
        return restResponse;
    }

    public static RestResponse failure(String message){
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(1);
        restResponse.setMessage(message);
        return restResponse;
    }

    public static RestResponse res(Integer code, String message){
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(code);
        restResponse.setMessage(message);
        return restResponse;
    }

    public RestResponse setSuccess(Boolean success) {
        if (success != null) put("success", success);
        return this;
    }

    public RestResponse setMessage(String message) {
        if (message != null) put("message", message);
        return this;
    }

    public RestResponse setData(Object data) {
        if (data != null) put("data", data);
        return this;
    }

    public RestResponse setPage(Integer page) {
        if (page != null) put("page", page);
        return this;
    }

    public RestResponse setLimit(Integer limit) {
        if (limit != null) put("limit", limit);
        return this;
    }

    public RestResponse setTotal(Long total) {
        if (total != null) put("total", total);
        return this;
    }

    public RestResponse setAny(String key, Object value) {
        if (key != null && value != null) put(key, value);
        return this;
    }

    public RestResponse setCode(Integer code) {
         put("code", code);
        return this;
    }
}
