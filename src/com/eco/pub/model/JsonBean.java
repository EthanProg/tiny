package com.eco.pub.model;

import java.util.List;
import java.util.Map;

/**
 * 
 * 本类为http请求后台解析JSON Bean
 * @author Ethan   
 * @version 1.0
 */
public class JsonBean {

	String status;// 状态码
	String msg;// 状态说明

	Map dataMap; 

	List dataList;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List getDataList() {
		return dataList;
	}

	public void setDataList(List dataList) {
		this.dataList = dataList;
	}

	public Map getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}
}
