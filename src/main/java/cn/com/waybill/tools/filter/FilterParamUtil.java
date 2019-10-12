package cn.com.waybill.tools.filter;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Set;

public class FilterParamUtil {

    public static final String API_ORDER_LIST = "pageNum,pageSize,userName,creator,createTime,updateTime";

    public static Object filterParam(Object o, String filters) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        Set<String> filterSet = Sets.newHashSet(Arrays.asList(filters.split(",")));
        filter.getExcludes().addAll(filterSet);
        String jsonString = JSONObject.toJSONString(o, filter);
        return JSONObject.parse(jsonString);
    }
}
