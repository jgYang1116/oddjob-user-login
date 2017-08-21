/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: JSONObjectSingleton.java
 * Author:   yjg
 * Date:     2017年8月21日 上午10:17:49
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.tool.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 功能描述: 
 * 
 * Author:   yjg@gongren.com
 * Date:     2017年8月21日 上午10:17:49
 */
public class JSONObjectSingleton {

	private static JSONObject jsonObject = new JSONObject();
	
	private JSONObjectSingleton() {}
	
	public static JSONObject getInstance() {
		return jsonObject;
	}
}
