/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: UserLoginVo.java
 * Author:   yjg
 * Date:     2017年8月15日 下午3:06:28
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.rpc.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * 功能描述: 
 * 
 * Author:   yjg@gongren.com
 * Date:     2017年8月15日 下午3:06:28
 */
public class UserLoginVo  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	/**
	 * token
	 */
	private String token;
	
	/**
	 * 用户Id
	 */
	private BigInteger userId;

	/**
	 * x坐标
	 */
	private Double xCoordinate;

	/**
	 * y坐标
	 */
	private Double yCoordinate;

	/**
	 * 设备类型(1001.ANDROID,1002.IOS,1003.网页)
	 */
	private Short appId;

	/**
	 * 登录IP
	 */
	private String LoginIp;
	
	/**
	 * app版本号
	 */
	private String appVersion;

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

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
	 * @return the xCoordinate
	 */
	public Double getxCoordinate() {
		return xCoordinate;
	}

	/**
	 * @param xCoordinate the xCoordinate to set
	 */
	public void setxCoordinate(Double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * @return the yCoordinate
	 */
	public Double getyCoordinate() {
		return yCoordinate;
	}

	/**
	 * @param yCoordinate the yCoordinate to set
	 */
	public void setyCoordinate(Double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/**
	 * @return the appId
	 */
	public Short getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(Short appId) {
		this.appId = appId;
	}

	/**
	 * @return the loginIp
	 */
	public String getLoginIp() {
		return LoginIp;
	}

	/**
	 * @param loginIp the loginIp to set
	 */
	public void setLoginIp(String loginIp) {
		LoginIp = loginIp;
	}

	/**
	 * @return the appVersion
	 */
	public String getAppVersion() {
		return appVersion;
	}

	/**
	 * @param appVersion the appVersion to set
	 */
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
}
