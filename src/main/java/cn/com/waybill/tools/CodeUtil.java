package cn.com.waybill.tools;


import java.nio.charset.Charset;
import java.util.UUID;

public class CodeUtil {

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }

    public static final String CODE_METHOD = "method";
    public static final String CODE_CONTENTTYPE = "contentType";
    public static final String CODE_INSTRUCTIONSPATH = "instructionsPath";

    public static final String DEFAULT_PASSWORD = "888888";  //默认密码

    public static final Charset cs = Charset.forName("UTF-8");

    //content-type编码格式
    public static final String CONTEXT_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public static final String CONTEXT_FORM_DATA = "application/form_data";
    public static final String CONTEXT_JSON = "application/json";

    public static final int HTTP_OK = 200;
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_DELETE = "DELETE";

    public static final String PASSWORD_SUFFIX = "*chuanqi";  //密码后缀
    public static final String TOKEN_SUFFIX = "*jsonwebtoken";  //token后缀
    public static final String API_TOKEN_SUFFIX = "*apitoken";  //微信用户token后缀
    public static final int REDIS_DBINDEX = 2;    //token报存在redis第二个库
    public static final String REDIS_PREFIX = "session:";
    public static final String REDIS_PREFIX_WEIXIN = "weixin:";
    public static final int REDIS_EXPIRE_TIME = 60 * 30;  //30分

    //人员类型
    public static final int USER_MIDDLEMAN = 0;  //中间商
    public static final int USER_CUSTOMER = 1;  //顾客

    //角色类型
    public static final int ROLE_ADMIN = 1;  //后台--管理员
    public static final int ROLE_USER = 2;  //后台--日常用户
    public static final int ROLE_BUYER = 3;  //后台--买手

    //文件下载地址
    public static final String FILE_ROOT_PATH = "file_root_path";

    //微信
    public static final String WX_CODE2SESSION = "code2Session";
    public static final String WX_APPID = "APPID";
    public static final String WX_SECRET = "SECRET";
    public static final String WX_JSCODE = "JSCODE";
}
