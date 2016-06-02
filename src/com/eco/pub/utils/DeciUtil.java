package com.eco.pub.utils;

import java.math.BigDecimal;

/**
 *
 * @Description：本类提供操作BigDecimal工具类
 * @author Ethan
 * @date 2016年3月18日
 * 
 * @Description：
 * @modifier
 */
public class DeciUtil {
	
	private static BigDecimal ZERO = new BigDecimal("0");
	
	private static BigDecimal HUND = new BigDecimal("100");
	
	/**
	 * 
	 * @Title: 
	 * @Description: 获取对象的BigDecimal类型
	 * @param o
	 * @return BigDecimal
	 * @author Ethan 2016年3月18日
	 *
	 * @Description:
	 * @modifier
	 */
	public static BigDecimal getBigDecimal(Object o) {
		BigDecimal temp = null;
		if (o == null) {
			temp = ZERO;
		} else if (o instanceof BigDecimal) {
			temp = (BigDecimal) o;
		} else {
			try {
				temp = new BigDecimal(o.toString());
			} catch (Throwable t) {
				return ZERO;
			}
		}
		return temp;
	}
	
	/**
	 * 
	 * @Title: 
	 * @Description: 使用示例：getBigDecimal(dataMap.get("CUST_NUM1"),0);
	 * @param o
	 * @param precision
	 * @return BigDecimal
	 * @author Ethan 2016年3月18日
	 *
	 * @Description: 
	 * @modifier
	 */
	public static BigDecimal getBigDecimal(Object o, int precision) {
		BigDecimal temp = getBigDecimal(o);
		try {
			temp = temp.setScale(precision, BigDecimal.ROUND_HALF_UP);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return temp;
	}
	
	/**
	 * 
	 * @Title: 
	 * @Description: 处理数值类型
	 * 若当前传入的是整数，则返回不包含小数
	 * 若传入是小数，则返回指定小数位数的数字
	 * @param o
	 * @param precision
	 * @return String
	 * @author Ethan 2016年3月18日
	 *
	 * @Description:
	 * @modifier
	 */
	public static String getDeciProcced(Object o, int precision) {
		BigDecimal bigDecimal = getBigDecimal(o,precision);
		String dicString = bigDecimal.toString();
		String[] sArray = dicString.split("\\.");
		String intPart = sArray[0];
		if (bigDecimal.compareTo(new BigDecimal(intPart)) == 0) {
			return intPart;
		}
		return dicString;
	}
	
	/**
	 * 
	 * @Title: 
	 * @Description: 返回数字与零的比较：1：大于，0：等于，-1:小于
	 * @param o
	 * @return int
	 * @author Ethan 2016年3月18日
	 *
	 * @Description: 
	 * @modifier
	 */
	public static int compareToZero(Object o) {
		BigDecimal bigDecimal = getBigDecimal(o);
		return bigDecimal.compareTo(ZERO);
	}
	
	
	/**
	 * 
	 * @Title: 
	 * @Description: 本方法获取o1对o2的占比, 默认为2精度
	 * @param o1
	 * @param o2
	 * @return String
	 * @author Ethan 2016年3月18日
	 *
	 * @Description: 
	 * @modifier
	 */
	public static String getPCT(Object o1, Object o2) {
		return getPCT(o1, o2, 2);
	}
	
	/**
	 * 
	 * @Title: 
	 * @Description: 本方法获取o1对o2的占比
	 * @param o1
	 * @param o2
	 * @param precision
	 * @return String
	 * @author Ethan 2016年3月18日
	 *
	 * @Description:
	 * @modifier
	 */
	public static String getPCT(Object o1, Object o2, int precision) {
		BigDecimal b1 = getBigDecimal(o1);
		BigDecimal b2 = getBigDecimal(o2);
		if (compareToZero(o2) != 1) {
			return "100";
		}
		return b1.multiply(HUND).divide(b2, precision, BigDecimal.ROUND_HALF_UP).toString();
	}
	
	/**
	 * 
	 * @Title: 
	 * @Description: 比较2个数,1:>,0:=,-1:<
	 * @param o1
	 * @param o2
	 * @return boolean
	 * @author Ethan 2016年3月25日
	 *
	 * @Description: 
	 * @modifier
	 */
	public static int compareWith(Object o1, Object o2) {
		return getBigDecimal(o1).compareTo(getBigDecimal(o2));
	}
	
	/**
	 * 
	 * @Title: 
	 * @Description: 取差值
	 * @param o1
	 * @param o2
	 * @return BigDecimal
	 * @author Ethan 2016年3月25日
	 *
	 * @Description:
	 * @modifier
	 */
	public static BigDecimal getDIF(Object o1, Object o2) {
		
		BigDecimal bigO1 = getBigDecimal(o1);
		
		BigDecimal bigO2 = getBigDecimal(o2);
		
		return bigO1.compareTo(bigO2) > 0 ? bigO1.subtract(bigO2) : bigO2.subtract(bigO1);
	}
}
