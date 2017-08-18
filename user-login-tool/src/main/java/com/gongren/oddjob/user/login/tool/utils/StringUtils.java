/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: StringUtils.java
 * Author:   yjg
 * Date:     2017年8月7日 上午10:24:27
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.tool.utils;

/**
 * 功能描述:
 * 
 * Author: yjg@gongren.com Date: 2017年8月7日 上午10:24:27
 */
public class StringUtils {

	/**
	 * 功能描述: 
	 * 判断字符串是否不为空
	 * @param str
	 * @return true 不为空  / false 为空
	 * Author:   yjg@gongren.com
	 * Date:     2017年8月7日 上午10:27:21
	 * Version: 1.0.0
	 */
	public static boolean isNotEmpty(String str) {
		return str != null && str.trim().length() > 0;
	}
	
	/**
	 * 功能描述: 
	 * 判断全部是否不为空
	 * @param str
	 * @return true/false
	 * Author:   yjg@gongren.com
	 * Date:     2017年8月7日 上午10:27:21
	 * Version: 1.0.0
	 */
	public static boolean isNotEmptyByAll(Object... objs) {
		for(Object obj : objs) {
			if(obj == null) 
				return false;
		}
		return true;
	}
	
	
}
