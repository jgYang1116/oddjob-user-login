/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: IOjUserLoginSender.java
 * Author:   yjg
 * Date:     2017年8月18日 下午4:03:32
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.rpc.rabbitmq.producer.api;

import com.gongren.oddjob.user.login.rpc.domain.entity.OjUserLogin;

/**
 * 功能描述: 
 * 
 * Author:   yjg@gongren.com
 * Date:     2017年8月18日 下午4:03:32
 */
public interface IOjUserLoginSender {

	void send(OjUserLogin userLogin);
	
}
