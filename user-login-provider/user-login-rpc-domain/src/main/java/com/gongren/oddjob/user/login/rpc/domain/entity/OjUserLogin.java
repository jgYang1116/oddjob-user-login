/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: OJUserLogin.java
 * Author:   yjg
 * Date:     2017年8月15日 下午2:06:30
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.rpc.domain.entity;

import java.math.BigInteger;

import com.gongren.oddjob.user.login.rpc.domain.basic.BasicDomain;

/**
 * 功能描述: 
 * 
 * Author:   yjg@gongren.com
 * Date:     2017年8月15日 下午2:06:30
 */
public class OjUserLogin extends BasicDomain{

	private static final long serialVersionUID = 1L;

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
	 * 是否过期(0.是,1.否) 说明:新的登录为1.旧的全部为0
	 */
	private Short isExpire;

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
	 * @return the isExpire
	 */
	public Short getIsExpire() {
		return isExpire;
	}

	/**
	 * @param isExpire the isExpire to set
	 */
	public void setIsExpire(Short isExpire) {
		this.isExpire = isExpire;
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
