/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: JsonResult.java
 * Author:   jgYang
 * Date:     2017年3月28日 下午9:53:34
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 功能描述:
 * 
 * Author: jgYang Date: 2017年3月28日 下午9:53:34
 */
@SuppressWarnings("serial")
@JsonInclude(Include.NON_NULL)
public class JsonResult<T> implements Serializable {
	/*DictionaryConstants.Result 0:成功 1:失败*/
	private int code;
	private String msg;
	private List<T> data;

	public JsonResult() {
		data = new ArrayList<T>();
	}

	public void addData(T element) {
		data.add(element);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}