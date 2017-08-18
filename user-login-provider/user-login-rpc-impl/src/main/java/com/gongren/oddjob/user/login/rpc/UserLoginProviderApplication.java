/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: UserLoginProviderApplication.java
 * Author:   yjg
 * Date:     2017年8月8日 下午12:16:23
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 功能描述:
 * 
 * Author: yjg@gongren.com Date: 2017年8月8日 下午12:16:23
 */
@SpringBootApplication
@ImportResource(value = { "classpath:spring-root.xml" })
public class UserLoginProviderApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserLoginProviderApplication.class, args);
	}
}
