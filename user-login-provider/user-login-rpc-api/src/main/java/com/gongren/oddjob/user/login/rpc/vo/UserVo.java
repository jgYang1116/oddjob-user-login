/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: UserVo.java
 * Author:   yjg
 * Date:     2017年8月14日 下午12:02:37
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.rpc.vo;

import java.math.BigInteger;

/**
 * 功能描述: 
 * 
 * Author:   yjg@gongren.com
 * Date:     2017年8月14日 下午12:02:37
 */
public class UserVo {

	private String password;
	private String username;
	private BigInteger userId;
	/**
	 * @return the userId
	 */
	public BigInteger getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
