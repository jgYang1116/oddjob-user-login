/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: TestServiceImpl.java
 * Author:   yjg
 * Date:     2017年8月8日 上午10:35:15
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.rpc.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.gongren.oddjob.user.login.rpc.api.ITestService;
import com.gongren.oddjob.user.login.rpc.redis.api.CacheService;


/**
 * 功能描述: 
 * 
 * Author:   yjg@gongren.com
 * Date:     2017年8月8日 上午10:35:15
 */
@Service(interfaceName="com.gongren.oddjob.user.login.rpc.api.ITestService")
public class TestServiceImpl implements ITestService {

	private final Logger logger =  LoggerFactory.getLogger(TestServiceImpl.class);
	
	@Autowired
	public CacheService cacheService;
	
	
	public String test(String str) {
		cacheService.putObject("123123123", str);
		logger.info("TestServiceImpl------------{}",str);
		return cacheService.getObject("123123123").toString();
	}

}
