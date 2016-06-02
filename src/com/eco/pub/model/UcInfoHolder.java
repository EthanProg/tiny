package com.eco.pub.model;


/**
 * 将上下文信息放入线程变量内
 * @author sunzhen
 *
 */
public class UcInfoHolder {

	private static ThreadLocal<UcInfo> ucInfoHolder = new ThreadLocal();

	public static void setUcInfo(UcInfo ucInfo){
		ucInfoHolder.set(ucInfo);
	}
	
	public static UcInfo getUcInfo(){
		return ((UcInfo)ucInfoHolder.get());
	}
	
	public static void clearUcInfo() {
	    ucInfoHolder.set(null);
	}
}
