/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: BasicDomain.java
 * Author:   yjg
 * Date:     2017年8月15日 下午2:04:14
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.rpc.domain.basic;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.dlg.commons.util.Utils;

/**
 * 功能描述: 
 * 
 * Author:   yjg@gongren.com
 * Date:     2017年8月15日 下午2:04:14
 */
public abstract class BasicDomain implements Serializable, Comparable<BasicDomain>{

	private static final long serialVersionUID = 1L;


	private BigInteger id;

	/**
	 * 0已删除 1有效
	 */
	private Short active = 1;

	/**
	 * 数据版本
	 */
	private Long version;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 创建人Id
	 */
	private BigInteger createUserId;

	/**
	 * 更新时间
	 */
	private Date modifyTime;

	/**
	 * 更新人Id
	 */
	private BigInteger modifyUserId;

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Short getActive() {
		return active;
	}

	public void setActive(Short active) {
		this.active = active;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigInteger getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(BigInteger createUserId) {
		this.createUserId = createUserId;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public BigInteger getModifyUserId() {
		return modifyUserId;
	}

	public void setModifyUserId(BigInteger modifyUserId) {
		this.modifyUserId = modifyUserId;
	}

	@Override
	public int compareTo(BasicDomain o) {
		return this.id.compareTo(o.getId());
	}

	@Override
	public String toString() {
		return Utils.json(this);
	}


}
