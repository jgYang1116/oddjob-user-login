/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: OjUserLoginReceiver.java
 * Author:   yjg
 * Date:     2017年8月18日 下午4:02:17
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.rpc.rabbitmq.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gongren.oddjob.user.login.rpc.domain.entity.OjUserLogin;
import com.gongren.oddjob.user.login.rpc.mysql.mappers.OjUserLoginMapper;
import com.gongren.oddjob.user.login.rpc.rabbitmq.BaseLogger;

/**
 * 功能描述: 
 * 
 * Author:   yjg@gongren.com
 * Date:     2017年8月18日 下午4:02:17
 */
@Component
@RabbitListener(queues = "loginReceiver") 
public class OjUserLoginReceiver extends BaseLogger {

	@Autowired
	private OjUserLoginMapper ojUserLoginMapper;
    
    @RabbitHandler
    public void process(OjUserLogin userLogin) {
    	logger.info(".........receiver start.........");
    	ojUserLoginMapper.updateIsExpire(userLogin.getUserId());
    	ojUserLoginMapper.save(userLogin);
    	logger.info(".........receiver end...........");
    }
}
