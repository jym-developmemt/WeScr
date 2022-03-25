/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.util;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import we.website.model.UserInfoModel;

/**
 * Token工具类
 *
 * @author cp0
 * @since 0.0
 */
public class TokenUtils {

	/**
	 * 创建Batch运行用户
	 *
	 * @param payload 内容
	 * @param secret 盐值
	 * @param timeout 超时时间
	 * @return JWT
	 */
	public static Authentication createBatchAuthentication() {
		// 超级管理员认证
		UserInfoModel userModel = new UserInfoModel();
		userModel.setUserId("BATCH");
		userModel.setUserName("BATCH");
		userModel.setStartDate(CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss"));
		userModel.setSysadmin(true);
		return new UsernamePasswordAuthenticationToken(
				userModel,
				null, userModel.getAuthorities());
	}
}
