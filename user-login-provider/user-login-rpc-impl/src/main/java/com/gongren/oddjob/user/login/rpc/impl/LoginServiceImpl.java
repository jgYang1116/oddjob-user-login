package com.gongren.oddjob.user.login.rpc.impl;

import java.math.BigInteger;
import java.util.Date;

import org.apache.shiro.authc.credential.PasswordMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dlg.commons.constant.DictionaryConstants;
import com.dlg.commons.util.AESUtils;
import com.dlg.commons.util.Utils;
import com.gongren.oddjob.user.login.rpc.api.ILoginService;
import com.gongren.oddjob.user.login.rpc.domain.entity.OjUserLogin;
import com.gongren.oddjob.user.login.rpc.rabbitmq.producer.api.IOjUserLoginSender;
import com.gongren.oddjob.user.login.rpc.redis.api.CacheService;
import com.gongren.oddjob.user.login.rpc.vo.UserLoginVo;
import com.gongren.oddjob.user.login.rpc.vo.UserVo;
import com.gongren.oddjob.user.login.tool.utils.JSONObjectSingleton;
import com.gongren.oddjob.user.login.tool.utils.StringUtils;
import com.gongren.project.commons.enums.SourceEnum;
import com.gongren.project.commons.enums.VerifyCodeEnum;
import com.gongren.project.commons.service.sms.api.service.ISmsRpcService;
import com.gongren.project.commons.service.sms.api.vo.VerifyCodeRpcVo;

@Service(interfaceName="com.gongren.oddjob.user.login.rpc.api.ILoginService")
public class LoginServiceImpl implements ILoginService {
	
	private final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

	private static String resource_token = "token";
	@Autowired
	private CacheService cacheService;
	@Autowired
	private PasswordMatcher credentialsMatcher;
	@Autowired
	private ISmsRpcService smsRpcService;
	@Autowired
	private IOjUserLoginSender sender;
	@Value("${token.key}")
	private String tokenKey;

	@Override
	public JSONObject login(String principal, int type, String credentials, String sms) {
		JSONObject resource = JSONObjectSingleton.getInstance();
		if(!StringUtils.isNotEmptyByAll(principal,type,credentials)) {
			resource.put("msg", "缺少必要参数");
			resource.put("code", DictionaryConstants.Result.FAIL);
			return resource;
		}
		if (StringUtils.isNotEmpty(sms))
			return loginByPassword(principal, type, credentials);
		return logingByCode(principal, type, credentials);
	}

	/**
	 * 功能描述: 验证码登录
	 * 
	 * @param principal
	 * @param type
	 * @param credentials
	 * @return Author: yjg@gongren.com Date: 2017年8月18日 下午7:39:56 Version: 1.0.0
	 */
	private JSONObject logingByCode(String principal, int type, String credentials) {
		JSONObject resource = JSONObjectSingleton.getInstance();;
		try {
			VerifyCodeRpcVo verifyCodeVo = new VerifyCodeRpcVo();
			verifyCodeVo.setPhone(principal);
			verifyCodeVo.setVerifyCode(credentials);
			verifyCodeVo.setSource(SourceEnum.ODD_JOB);
			VerifyCodeEnum verifyCodeEnum = smsRpcService.checkCodeAndDelete(verifyCodeVo);
			if (null == verifyCodeEnum || !VerifyCodeEnum.SUCCESS.equals(verifyCodeEnum)) {
				resource.put("msg", "验证码错误");
				resource.put("code", DictionaryConstants.Result.FAIL);
				return resource;
			}
			resource.put(resource_token, AESUtils.encryptString(principal + "_" + type, tokenKey));
			resource.put("msg", "成功");
			resource.put("code", DictionaryConstants.Result.SUCCESS);
		} catch (Exception e) {
			logger.error("登录使用短信认证失败",e);
			resource.put("msg", "验证码错误");
			resource.put("code", DictionaryConstants.Result.FAIL);
		}
		return resource;
	}

	/**
	 * 功能描述: 密码登录
	 * 
	 * @param principal
	 * @param type
	 * @param password
	 * @return Author: yjg@gongren.com Date: 2017年8月8日 下午7:39:54 Version: 1.0.0
	 */
	private JSONObject loginByPassword(String principal, int type, String password) {
		JSONObject resource = JSONObjectSingleton.getInstance();
		try {
			//TODO 测试数据 start
			UserVo vo = new UserVo();
			vo.setPassword("123456");
			vo.setUserId(new BigInteger("789302903151136768"));
			cacheService.putObject(principal + "_" + type, JSON.toJSONString(vo));
			//TODO 测试数据 end
			
			String json = (String) cacheService.getObject(principal + "_" + type);
			JSONObject jsonObject = JSON.parseObject(json);
			if (jsonObject == null) {
				resource.put("msg", "密码错误");
				resource.put("code", DictionaryConstants.Result.FAIL);
				return resource;
			}

			String realPassword = (String) jsonObject.get("password");
			String encryptPassword = Utils.encryptPassword(password, credentialsMatcher.getPasswordService());
			if (realPassword != null && encryptPassword != null && Utils.matchPassword(realPassword, encryptPassword, credentialsMatcher.getPasswordService())) {
				resource.put(resource_token, AESUtils.encryptString(principal + "_" + type, tokenKey));
				resource.put("msg", "成功");
				resource.put("code", DictionaryConstants.Result.SUCCESS);
			} else {
				resource.put("msg", "密码错误");
				resource.put("code", DictionaryConstants.Result.FAIL);
			}
		} catch (Exception e) {
			logger.error("登录失败", e);
		}
		return resource;
	}

	@Override
	public JSONObject saveUserLogin(UserLoginVo userLogin) {
		JSONObject resource = new JSONObject();
		try {
			String decryptString = AESUtils.decryptString(userLogin.getToken(), tokenKey);
			String json = (String) cacheService.getObject(decryptString);
			JSONObject jsonObject = JSON.parseObject(json);
			if (jsonObject != null && jsonObject.get("userId") != null) {
				BigInteger userId = new BigInteger(String.valueOf(jsonObject.get("userId")));
				userLogin.setUserId(userId);
				OjUserLogin ojUserLogin = new OjUserLogin();
				Utils.copyNotNullObject(userLogin, ojUserLogin);
				ojUserLogin.setId(Utils.id());
				ojUserLogin.setCreateTime(new Date());
				ojUserLogin.setCreateUserId(userId);
				ojUserLogin.setVersion(0L);
				ojUserLogin.setIsExpire((short) 1);
//				ojUserLoginMapper.updateIsExpire(userId);
//				ojUserLoginMapper.save(ojUserLogin);
				sender.send(ojUserLogin);
			}
			resource.put("msg", "成功");
			resource.put("code", DictionaryConstants.Result.SUCCESS);
		} catch (Exception e) {
			logger.error("保存登录信息异常",e);
			resource.put("msg", "保存登录信息");
			resource.put("code", DictionaryConstants.Result.FAIL);
		}
		
		return resource;
	}
	
}
