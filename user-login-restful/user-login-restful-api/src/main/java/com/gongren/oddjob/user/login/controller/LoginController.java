/*
 * Copyright (C), 2013-2017, 工人网
 * FileName: LoginController.java
 * Author:   yjg
 * Date:     2017年8月8日 上午9:34:10
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.gongren.oddjob.user.login.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.dlg.commons.constant.DictionaryConstants;
import com.gongren.oddjob.user.login.rpc.api.ILoginService;
import com.gongren.oddjob.user.login.rpc.api.ITestService;
import com.gongren.oddjob.user.login.rpc.vo.UserLoginVo;
import com.gongren.oddjob.user.login.tool.utils.StringUtils;

/**
 * 功能描述:
 * 
 * Author: yjg@gongren.com Date: 2017年8月8日 上午9:34:10
 */
@RestController
public class LoginController extends BaseController {

	@Reference
	public ITestService testService;

	@Reference
	public ILoginService loginService;

	@RequestMapping("/test/{name}")
	@ResponseBody
	public JsonResult<String> test() {
		JsonResult<String> jsonResult = new JsonResult<String>();
		logger.info("请求成功-----");
		jsonResult.addData(testService.test("123"));
		return jsonResult;
	}

	/**
	 * 
	 * 功能描述:
	 * 
	 * @param principal 手机号码
	 * @param credentials 密码或验证码
	 * @param type 用户类型 1.个人 2企业
	 * @param sms 登录方式为验证码时：必填
	 * @return Author: yjg@gongren.com Date: 2017年8月18日 下午2:06:12 Version: 1.0.0
	 */
	@RequestMapping("/login")
	@ResponseBody
	public JsonResult<String> login(@RequestParam(value = "principal", required = true) String principal,
			@RequestParam(value = "credentials", required = true) String credentials,
			@RequestParam(value = "type", required = true) Integer type, String sms) {
		JsonResult<String> jsonResult = new JsonResult<String>();
		try {
			JSONObject jsonObject = loginService.login(principal, type, credentials, sms);
			jsonResult.setCode((Integer) jsonObject.get("code"));
			jsonResult.setMsg((String) jsonObject.get("msg"));
			jsonResult.addData(jsonResult.getCode() == DictionaryConstants.Result.SUCCESS ? String.valueOf(jsonObject.get("token")) : "");
		} catch (Exception e) {
			jsonResult.setCode(DictionaryConstants.Result.FAIL);
			jsonResult.setMsg("登录异常");
			logger.error("登录异常", e);
		}
		return jsonResult;
	}

	/**
	 * 功能描述:
	 * 
	 * @param userLogin
	 * @return Author: yjg@gongren.com Date: 2017年8月18日 下午2:07:27 Version: 1.0.0
	 */
	@RequestMapping("/saveUserLogin")
	@ResponseBody
	public JsonResult<String> saveUserLogin(UserLoginVo userLogin) {
		JsonResult<String> jsonResult = new JsonResult<String>();
		try {
			if (!StringUtils.isNotEmptyByAll(userLogin.getxCoordinate(), userLogin.getyCoordinate(),
					userLogin.getAppId())) {
				throw new RuntimeException("缺少必要参数");
			}
			JSONObject jsonObject = loginService.saveUserLogin(userLogin);
			jsonResult.setCode((Integer) jsonObject.get("code"));
			jsonResult.setMsg((String) jsonObject.get("msg"));
		} catch (Exception e) {
			jsonResult.setCode(DictionaryConstants.Result.FAIL);
			jsonResult.setMsg(e.getMessage());
			logger.error("登录异常", e);
		}
		return jsonResult;
	}

}
