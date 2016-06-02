package com.eco.pub.tags;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;

/**
 *
 * 类描述：工具类，方便获取和当前皮肤相关的各种资源：图片、js、css
 *
 * @author Ethan
 * @date 2016年3月3日
 * 
 * 修改描述：
 * @modifier
 */
public final class SkinUtils {

	// 默认apache资源父级路径
	public static String path_prefix = "/skin/";

	/**
	 * 
	 * 方法描述: 获取当前皮肤的某个JS文件的完整访问路径
	 * 
	 * @param request
	 * @param jsName
	 * @return String
	 * @author Ethan 2016年3月5日
	 *
	 * 修改描述：
	 * @modifier 
	 */
	public static String getJS(ServletRequest request, String jsName) {

		String path = "";

		if (jsName.startsWith("/")) {
			path = jsName;
		} else {
			path = path_prefix + jsName;
		}
		return replaceSpecialChar(path);
	}

	/**
	 * 
	 * 方法描述: 获取当前皮肤的某个CSS的完整访问路径
	 * 
	 * @param request
	 * @param cssName
	 * @return String
	 * @author Ethan 2016年3月5日
	 *
	 * 修改描述：
	 * @modifier 
	 */
	public static String getCSS(ServletRequest request, String cssName) {

		String path = "";

		if (cssName.startsWith("/")) {
			path = cssName;
		} else {
			path = path_prefix + cssName;
		}
		return replaceSpecialChar(path);
	}

	private static String replaceSpecialChar(String str) {
		if (str == null) {
			return null;
		}
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		String dest = m.replaceAll("");
		return dest;
	}
}
