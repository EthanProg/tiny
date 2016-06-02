package com.eco.pub.model;

/**
 * 接口返回信息枚举
 * Created by Ethan on 2016/1/22.
 */
public class Code {

    /**
     * CODE:000
     * MSG:执行成功
     */
    final public static String SUCCESS = "000";
    /**
     * CODE:100
     * MSG:用户不存在
     */
    final public static String USER_NOTEXIST = "100";

    /**
     * CODE:101
     * MSG:用户已停用
     */
    final public static String DISABLED = "101";
    /**
     * CODE:102
     * MSG:用户密码不正确
     */
    final public static String PWD_FAIL = "102";
    /**
     * CODE:103
     * MSG:无访问权限
     */
    final public static String NO_ACCESS = "103";
    /**
     * CODE:104
     * MSG:服务不存在或路由未配置
     */
    final public static String ROUTE_NOEXIST = "104";

    /**
     * CODE:500
     * MSG:执行失败(自定义错误信息)
     */
    final public static String ERROR_GENERAL = "500";

    /**
     * CODE:501
     * MSG:获取参数失败
     */
    final public static String PARM_FAIL = "501";

    /**
     * CODE:502
     * MSG:获取参数为空
     */
    final public static String PARM_NOEXIST = "502";
    /**
     * CODE:503
     * MSG:解析参数出错
     */
    final public static String ERROR_PARMEXPLAN = "503";
    /**
     * CODE:504
     * MSG:运行时异常
     */
    final public static String ERROR_RUNTIME = "504";
    /**
     * CODE:505
     * MSG:应用系统错误(增删改查的错误)
     */
    final public static String ERROR_APP = "505";
    /**
     * CODE:506
     * MSG:Encoding 异常
     */
    final public static String EXCEPT_ENCODING_ERROR = "506";

    /**
     * CODE:507
     * MSG:xml解析错误
     */
    final public static String EXCEPT_SAXPARSE_ERROR = "507";

    /**
     * CODE:508
     * MSG:违反数据完整性异常
     */
    final public static String EXCEPT_INTEGRITY_VIOLATION_ERROR = "508";

    /**
     * CODE:509
     * MSG:IO异常
     */
    final public static String EXCEPT_IO_ERROR = "509";

    /**
     * CODE:510
     * MSG: WEB SERVICE 初始出错
     */
    final public static String SERVICE_ERROR = "510";

    /**
     * CODE:511
     * MSG: WEB SERVICE URL出错
     */
    final public static String URL_ERROR = "511";

    /**
     * CODE:512
     * MSG: WEB SERVICE 服务地址出错
     */
    final public static String REMOTE_ERROR = "512";

    /**
     * CODE:513
     * MSG: DOM解析时出错
     */
    final public static String DOCUMENT_EROR = "513";

    /**
     * 接口返回信息
     */
    public static String getMsg(String code) {
        String message = "未知错误";
        if (code.equals(SUCCESS)) {
            message = "执行成功！";
        } else if (code.equals(USER_NOTEXIST)) {
            message = "用户不存在！";
        } else if (code.equals(DISABLED)) {
            message = "用户已停用！";
        } else if (code.equals(PWD_FAIL)) {
            message = "用户密码不正确！";
        } else if (code.equals(NO_ACCESS)) {
            message = "服务无访问权限！";
        } else if (code.equals(ROUTE_NOEXIST)) {
            message = "服务不存在或路由未配置！";
        } else if (code.equals(ERROR_GENERAL)) {
            message = "执行失败！";
        } else if (code.equals(PARM_FAIL)) {
            message = "获取参数失败！";
        } else if (code.equals(PARM_NOEXIST)) {
            message = "获取参数为空！";
        } else if (code.equals(ERROR_PARMEXPLAN)) {
            message = "解析参数出错！";
        } else if (code.equals(ERROR_RUNTIME)) {
            message = "运行时异常！";
        } else if (code.equals(ERROR_APP)) {
            message = "应用系统错误！";
        } else if (code.equals(EXCEPT_ENCODING_ERROR)) {
            message = "编码出现错误！";
        } else if (code.equals(EXCEPT_SAXPARSE_ERROR)) {
            message = "xml解析错误！";
        } else if (code.equals(EXCEPT_INTEGRITY_VIOLATION_ERROR)) {
            message = "违反数据完整性异常";
        } else if (code.equals(EXCEPT_IO_ERROR)) {
            message = "IO异常";
        } else if (code.equals(SERVICE_ERROR)) {
            message = "WEB SERVICE 出错";
        } else if (code.equals(URL_ERROR)) {
            message = "WEB SERVICE URL出错";
        } else if (code.equals(REMOTE_ERROR)) {
            message = "WEB SERVICE 服务地址出错";
        } else if (code.equals(DOCUMENT_EROR)) {
            message = "DOM解析时出错";
        }
        return message;
    }
}
