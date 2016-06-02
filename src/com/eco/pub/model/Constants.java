package com.eco.pub.model;

/**
 * 功能：常量类
 * @author 郑斌	2014年7月18日 下午5:07:29
 * 修改说明：
 */
public class Constants {

	//应用级缓存的key
	public static final String CONTEXT_KEY_CACHE = "application_cache";
	//枚举缓存的key
	public static final String CONTEXT_KEY_ENUMS = "application_enums";
	//权限控制Map的key
	public static final String CONTEXT_KEY_PERMISSION = "application_permission";
	public static final String CONTEXT_KEY_PERMISSIONKEY = "application_permissionkey";
	//全局缓存中的key
	public static final String CACHE_SITENAME = "sitename";
	public static final String CACHE_TNPM = "tnpm";
	public static final String CACHE_PAGESIZE = "pagesize";
	public static final String CACHE_EMAIL = "email";
	public static final String CACHE_THEME = "theme";
	public static final String CACHE_TIMEZONE = "timezone";
	
	//菜单配置JSONArray的key
	public static final String CONTEXT_KEY_MENU = "application_menu";
	
	/**
	 * 会话中用户的key
	 */
	public static final String SESSION_KEY_LOGINUSER = "session_longinuser";
	
	/**
	 * st会话中用户的key
	 */
	public static final String SESSION_KEY_STLOGINUSER = "session_stlonginuser";
	
	/**
	 * sEUC(电商）会话中用户的key
	 */
	public static final String SESSION_KEY_EUCLOGINUSER = "session_euclonginuser";
	
	/**
	 * 电商IDP的配置
	 */
	public static final String E_IDP = "euc";
	
	/**
	 * 第三方应用的会话key
	 */
	public static final String SESSION_KEY_THIRDAPPMAP = "session_thirdappmap";
	
	//redirect时传递参数，缓存到session中的key
	public static final String REDIRECT_ATTR_KEY = "redirect_attributes_";
	
	
	/**
	 * lambo单点登录时加密信息放入cookie中的key
	 */
	public static final String LAMBO_SSO_COOKIE_KEY = "lambo_sso_cookie_key";
}
