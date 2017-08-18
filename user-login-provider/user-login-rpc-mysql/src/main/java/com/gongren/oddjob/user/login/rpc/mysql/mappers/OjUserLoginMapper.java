package com.gongren.oddjob.user.login.rpc.mysql.mappers;

import java.math.BigInteger;

import org.apache.ibatis.annotations.Param;

import com.gongren.oddjob.user.login.rpc.domain.entity.OjUserLogin;
import com.gongren.oddjob.user.login.rpc.mysql.annotation.DlgRepository;

@DlgRepository
public interface OjUserLoginMapper {
	
	/**
	 * 功能描述: 
	 * 保存登录信息
	 * @param ojUserLogin
	 * Author:   yjg@gongren.com
	 * Date:     2017年8月17日 下午4:34:32
	 * Version: 1.0.0
	 */
    void save(@Param("userLogin") OjUserLogin ojUserLogin);

	/**
	 * 功能描述: 
	 * 更新登录记录为过期
	 * @param userId
	 * Author:   yjg@gongren.com
	 * Date:     2017年8月17日 下午4:34:32
	 * Version: 1.0.0
	 */
	void updateIsExpire(@Param("userId") BigInteger userId);
	
}
