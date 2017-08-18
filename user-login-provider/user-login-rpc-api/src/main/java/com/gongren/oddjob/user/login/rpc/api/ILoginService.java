/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: ILoginService.java
 * Author:   yjg
 * Date:     2017年8月8日 下午6:26:02
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.rpc.api;

import com.alibaba.fastjson.JSONObject;
import com.gongren.oddjob.user.login.rpc.vo.UserLoginVo;

/**
 * 功能描述: 
 * 
 * Author:   yjg@gongren.com
 * Date:     2017年8月8日 下午6:26:02
 */
public interface ILoginService {

	/**
	 * 功能描述: 
	 * 
	 * @param principal 手机号码
	 * @param type 用户类型 1个人 2企业
	 * @param credentials 密码或验证码
	 * @param sms 登录方式为验证码时：必填【不为空即可】
	 * @return
	 * Author:   yjg@gongren.com
	 * Date:     2017年8月8日 下午7:31:53
	 * Version: 1.0.0
	 */
	JSONObject login(String principal, int type, String credentials, String sms);

	/**
	 * 功能描述: 
	 * 保存用户登录信息
	 * @param userLogin
	 * @return
	 * Author:   yjg@gongren.com
	 * Date:     2017年8月18日 下午2:09:23
	 * Version: 1.0.0
	 */
	JSONObject saveUserLogin(UserLoginVo userLogin);

	
}
