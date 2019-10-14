package cn.com.waybill.tools;

public class MessageCode {

    public static final int BASE_SUCC_CODE = 0;                             // 请求成功
    public static final int BASE_PARAMS_ERR_VALIDE = 911001;                // 请填写完整参数
    public static final int BASE_FILE_ERR_UP = 911002;      // 文件上传失败
    public static final int BASE_FILE_ONLY_UP = 911003;    //只能上传单个文件
    public static final int BASE_FILE_NULL = 911004;       //文件不存在
    public static final int BASE_FILE_COPY_ERR = 911005;       //文件拷贝失败
    public static final int BASE_FILE_UP_TYPE_ERR = 911006;       //文件上传格式有误

    //请求验证
    public static final int AUTH_HEADER_USER_NULL = 90101; //头信息中缺少 用户 信息
    public static final int AUTH_HEADER_TOKEN_NULL = 90102; //头信息中缺少 token 信息
    public static final int AUTH_HEADER_TIMESTAMP_FILED = 90103; //头信息中缺少 TIMESTAMP 信息
    public static final int AUTH_HEADER_TOKEN_FILED = 90104; //token验证失败
    public static final int AUTH_HEADER_TOKEN_TIMEOUT = 90105; //token已失效
    public static final int AUTH_HEADER_USER_FILED = 90106; //用户验证失败
    public static final int AUTH_HEADER_VALID_NULL = 90107; //用户权限不足
    public static final int AUTH_HEADER_TIMESTAMP_ERR = 90107; //TIMESTAMP信息不一致

    //订单
    public static final int ORDER_ERR_SAVE = 10101; //订单保存失败
    public static final int ORDER_ERR_SELECT = 10102; //订单查询失败
    public static final int ORDER_ERR_DEL = 10103; //订单删除失败
    public static final int ORDER_SEC_PHONE = 10104; //请填写手机号
    public static final int APPINFO_CERT_EXIT = 10106; //请先删除该应用中的证书
    public static final int APPINFO_ERR_DEL = 10107; //应用删除失败
    public static final int APPINFO_ERR_TOKEN = 10108;   // 应用票据获取失败
    public static final int APPINFO_ERR_SECURITY = 10109;    // 应用凭证验证失败
    public static final int APPINFO_ERR_MESSAGEBODY = 10110;   // 消息内容不能为空
    public static final int APPINFO_SERVER_ERR_USERS_NULL = 10111; // 用户不能为空

    //快递
    public static final int CERT_ERR_SAVE = 11101; //快递公司失败
    public static final int CERT_ERR_DELETE = 11102; //删除证书失败
    public static final int CERT_NULL = 11103; //证书不存在
    public static final int CERT_FILE_ERR_DEL = 11104; //删除证书文件失败
    public static final int CERT_KEY_ERR_DEL = 11105; //删除证书key文件失败
    public static final int CERT_APKNAME_BIND_MORE = 11106; //当前证书已绑定其他应用，请重新输入
    public static final int CERT_BIND_NULL = 11107; //请上传相应的证书文件

    //讨论组
    public static final int DISCUSS_NULL_SELECT = 12101; //讨论组不存在
    public static final int DISCUSS_ERR_SAVE = 12102; //保存讨论组失败
    public static final int DISCUSS_ERR_DEL = 12103; //删除讨论组失败
    public static final int DISCUSS_USER_ERR_SAVE = 12104; //保存讨论组成员失败
    public static final int DISCUSS_USER_ERR_DEL = 12105; //删除讨论组成员失败
    public static final int DISCUSS_USER_EXIT = 12106; //讨论组成员已存在

    //应用配置
    public static final int CONFIG_ERR_ADD = 13101; //应用配置添加失败

    //用户
    public static final int USERINFO_ERR_SELECT = 20101; //用户不存在
    public static final int USERINFO_DISABLE = 20102; //用户已被禁用
    public static final int USERINFO_ERR_PASSWORD = 20103; //密码错误
    public static final int USERINFO_ERR_LOGIN = 20104; //用户登陆失败
    public static final int USERINFO_ERR_SAVE = 20105; //保存用户失败
    public static final int USERINFO_ERR_DEL = 20106; //删除用户失败
    public static final int USERINFO_PASSWORD_NULL = 20107; //请输入密码
    public static final int USERINFO_EXIST = 20108; //用户已存在
    public static final int USERINFO_PARAM_NULL = 20109; //请填写完整登录信息
    public static final int USERINFO_ERR_OLDPASS = 20110; //原密码错误
    public static final int USERINFO_ERR_RESETPASS = 20111; //修改密码失败
    public static final int USERINFO_DETAIL_ERR_ADD = 20112; //保存用户登陆信息失败
    public static final int USERINFO_ERR_QUERY_PAGE = 20113; //请填写完整的分页信息

    //角色
    public static final int ROLE_ERR_SAVE = 21101; //角色保存失败
    public static final int ROLE_PERM_ERR_ADD = 21103; //角色权限增加失败
    public static final int ROLE_PERM_ERR_DEL = 21104; //角色权限重置失败
    public static final int ROLE_EXIT_CODE = 21105; //角色编码已存在
    public static final int ROLE_USED_ING = 21106; //当前角色已被使用，请先解除使用
    public static final int ROLE_ERR_DELETE = 21107; //角色删除失败

    //权限
    public static final int PERM_ERR_SAVE = 22101; //权限保存失败
    public static final int PERM_ERR_DEL = 22102; //权限删除失败

    //公司
    public static final int COMPANYINFO_ERR_SELECT = 23101; //未查到公司
    public static final int COMPANYINFO_ERR_SAVE = 23102; //保存公司失败
    public static final int COMPANYINFO_ERR_DELECT = 23103; //删除公司失败
    public static final int COMPANYINFO_NOT_PASS = 23104; //公司未通过审核或者已被禁用，请联系管理员

    //离线推送
    public static final int OFFLINEPUSH_ERR_REGISTER = 24101; //离线推送注册失败
    public static final int OFFLINEPUSH_ERR_UNREGISTER = 24102; //离线推送注销失败

    //平台
    public static final int PLATFORM_NEXIST_ERR = 251001;    // 平台不存在
    public static final int PLATFORM_ERR_VERSION = 251002;   // 版本号不正确
    public static final int PLATFORM_ERR_TOKEN = 251003;    // 登陆缓存失败

    //推送
    public static final int EXTERNALPUSH_NULL = 261001;    // 推送记录不存在
    public static final int EXTERNALPUSH_STATE_ERR = 261002;    // 推送状态修改失败
    public static final int EXTERNALPUSH_ERR_PUSH = 261003;      // 消息发送失败
    public static final int MESSAGE_ERR_MSGREPART = 261004;      // 发送重复消息
    public static final int MESSAGE_ERR_SAVE = 261005;                       //消息保存失败
    public static final int MESSAGE_BODY_ERR_SAVE = 261006;                  //消息内容保存失败
    public static final int MESSAGE_PUSH_ERROR_STAT_NULL = 261007;          // 未找到消息推送
    public static final int MESSAGE_SELECT_NULL = 261008;          // 消息未找到
    public static final int MESSAGE_ERR_OFFLINE_PUSH = 261009;     //离线消息发送失败

    public static final int COMMONCONFIG_ERR_SAVE = 271001;          // 全局配置保存失败
    public static final int COMMONCONFIG_ERR_DEL = 271002;          // 全局配置删除失败
    public static final int COMMONCONFIG_FROM_NULL = 271003;          // 源版本配置不存在
    public static final int COMMONCONFIG_TO_NULL = 271004;          // 目标版本不存在

    public static final int WX_CONNECT_ERR = 281001;          // 微信服务器连接失败
    public static final int WX_VALID_ERR = 281002;          // 微信验证失败

}
