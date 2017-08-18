/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: OjUserLoginSenderServiceImpl.java
 * Author:   yjg
 * Date:     2017年8月18日 下午4:04:26
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.rpc.rabbitmq.producer.impl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gongren.oddjob.user.login.rpc.domain.entity.OjUserLogin;
import com.gongren.oddjob.user.login.rpc.rabbitmq.BaseLogger;
import com.gongren.oddjob.user.login.rpc.rabbitmq.producer.api.IOjUserLoginSender;

/**
 * 功能描述: 
 * 
 * Author:   yjg@gongren.com
 * Date:     2017年8月18日 下午4:04:26
 */
@Component
public class OjUserLoginSenderServiceImpl extends BaseLogger implements IOjUserLoginSender {

	@Autowired
    private AmqpTemplate rabbitTemplate;

    @Override
    public void send(OjUserLogin userLogin) {
    	logger.info("..........sender start.........");
        rabbitTemplate.convertAndSend("loginReceiver", userLogin);
        logger.info("..........sender end.........");
    }

}
