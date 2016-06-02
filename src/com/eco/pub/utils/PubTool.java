package com.eco.pub.utils;

import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.eco.pub.model.JsonBean;
import com.eco.pub.model.Code;

/**
 * 应用公用工具类
 * Created by Ethan on 2016/1/21.
 */
public class PubTool {

    /**
     * 组织Response对象
     *
     * @param msg
     * @param status
     * @param map
     * @param list
     * @return
     * By Ethan 2015-1-22
     */
    public static Map responseData(String msg, String status, Map map, List list){
        Map resMap = new HashMap();
        resMap.put("msg", msg);
        resMap.put("status", status);
        resMap.put("dataMap",map);
        resMap.put("dataList",list);
        return resMap;
    }


    /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     *
     * @return
     * By Ethan 2015-1-22
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 
     * @Title: 
     * @Description: 判断是否为空或者null
     * @param s
     * @return boolean
     * @author Ethan 2016年3月18日
     *
     * @Description:
     * @modifier
     */
    public static boolean isNullOrEmpty(String s) {
		return "".equals(s) || null == s;
	}
    
    /**
     * 
     * @Title: 
     * @Description: 判断不为空，且不为null
     * @param s
     * @return boolean
     * @author Ethan 2016年3月18日
     *
     * @Description:
     * @modifier
     */
    public static boolean isNotNullAndEmpty(String s) {
		return !isNullOrEmpty(s);
	}
    
    /**
     * 
     * @Title: 
     * @Description: 判断List中有数据
     * @param l
     * @return boolean
     * @author Ethan 2016年3月18日
     *
     * @Description:
     * @modifier
     */
    public static boolean isListHasData(List<Object> l) {
		return l != null && l.size() > 0;
	}
    
    /**
     * 
     * @Title: 
     * @Description: 判断是否为数字(数字前面可带正负号)：如果为数字，返回true，否则返回false
     * @param str
     * @return boolean
     * @author Ethan 2016年3月18日
     *
     * @Description:
     * @modifier
     */
	public static boolean isNumMinus(final String str) {
		if (str == null || "".equals(str))
			return false;
		Pattern pattern = Pattern.compile("(^((-)|(\\+))?[0-9](\\.[0-9]+)?$)|(^((-)|(\\+))?([1-9][0-9]*)(\\.[0-9]+)?$)");
		Matcher matcherCredit = pattern.matcher(str);
		return matcherCredit.matches();
	}
	
	/**
	 * 
	 * @Title: 
	 * @Description: 判断是否为数字(数字前面不带正负号)：如果为数字，返回true，否则返回false
	 * @param str
	 * @return boolean
	 * @author Ethan 2016年3月18日
	 *
	 * @Description: 
	 * @modifier
	 */
	public static boolean isNum(final String str) {
    	if(str == null || "".equals(str))
    		return false;
        Pattern pattern = Pattern.compile("(^[0-9](\\.[0-9]+)?$)|(^([1-9][0-9]*)(\\.[0-9]+)?$)");
        Matcher matcherCredit = pattern.matcher(str);
        return matcherCredit.matches();
    }
    
    /**
     * 
     * 方法描述: 本方法处理向客户端返回认证信息
     * 
     * @param response
     * @param status
     * @author Ethan 2016年3月3日
     *
     * 修改描述：
     * @modifier
     */
    public static void responseAuthenInfo(HttpServletResponse response, String status) {
    	JsonBean data = new JsonBean();
		data.setMsg(Code.getMsg(status));
		data.setStatus(status);

		Gson gson = new Gson();
		String jsonParm = gson.toJson(data);  
		
		response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        
		PrintWriter writer = null;
		
		try {
			writer = response.getWriter();
			writer.write(jsonParm);
			response.getWriter().flush();
		} catch (RuntimeException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
			if (null != writer) {
				writer.close();
			}
		}
	}
    
    
    /**
     * 
     * 方法描述: 功能:本类为排序内部类  针对list中map的某个或某两个参数值进行正序或倒序排序，
     * 调用方式如下增加两句代码即可（正序：asc  倒序：desc）
     * 注意，需要排序的参数必须均为数值类型，另外排序的参数为要排序的list中map的key值;且List.size()>1
     * PubTool.ComparatorList comparator=new PubTool.ComparatorList("asc","第一个要排序的参数","第二个要排序的参数(该参数可选)");
     * Collections.sort(需要排序的list, comparator);
     * @author Ethan 2016年3月3日
     *
     * 修改描述：
     * @modifier
     */
    public static class ComparatorList implements Comparator, Serializable {
        private static final long serialVersionUID = 1L;
		String c1 = "";// 第一个排序参数
		String c2 = "";// 第二个排序参数(可选)
		String sortWay = "";// 排序方式 asc：正序 desc：倒序
		public ComparatorList(String sort, String s1, String s2) {
			sortWay = sort;
			c1 = s1;
			c2 = s2;
		}
		public ComparatorList(String sort, String s1) {
			sortWay = sort;
			c1 = s1;
		}

		public int compare(Object arg0, Object arg1) {
			Map map0 = (Map) arg0;
			Map map1 = (Map) arg1;
			int flag = 0;
			// 首先比较第一个参数值，如果第一个参数值相同，则比较第二个参数值(如果有第二个参数的话)
			// sortWay 排序方式 asc：正序 desc：倒序
			if (sortWay.equals("asc")) {// 正序
				flag = (new BigDecimal(map0.get(c1).toString()).compareTo(new BigDecimal(map1.get(c1).toString())));
				if (flag == 0 && !c2.equals("")) {
					flag = (new BigDecimal(map0.get(c2).toString()).compareTo(new BigDecimal(map1.get(c2).toString())));
				}
			} else if (sortWay.equals("desc")) {// 倒序
				flag = (new BigDecimal(map1.get(c1).toString()).compareTo(new BigDecimal(map0.get(c1).toString())));
				if (flag == 0 && !c2.equals("")) {
					flag = (new BigDecimal(map1.get(c2).toString()).compareTo(new BigDecimal(map0.get(c2).toString())));
				}
			}
			return flag;
		}
	}
}
