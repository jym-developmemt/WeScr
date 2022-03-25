/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.auth.grantor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.core.auth.AuthGrantor;
import we.core.auth.AuthGrantorType;
import we.core.model.UserGrantedAuthorityModel;
import we.website.model.UserInfoModel;
import we.website.service.UserInfoService;

/**
 * 用户校验器
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class UserIdGrantor implements AuthGrantor {

	@Autowired
	private UserInfoService userInfoService;


	/**
	 * 权限类型
	 */
	@Override
	public String getAuthType() {
		return AuthGrantorType.USER_ID;
	}

	/**
	 * 检查用户是否有该权限
	 */
	public boolean hasAuthority(Authentication userInfo, String args) throws Exception {
		// 匿名用户以外
		if (StringUtils.isEmpty(userInfo.getName()) || "anonymousUser".equals(userInfo.getName())) {
			return false;
		}

		// 用户ID表达式
		String expression = args;
		for (String userIdExpression : expression.split(",")) {
			// 转为正则表达式进行判断
			String regex = userIdExpression.replaceAll("%", "\\\\w*").replaceAll("_", "\\\\w");
			regex = "^" + regex + "$";
			if (Pattern.matches(regex, userInfo.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 取得用户权限
	 */
	public List<UserGrantedAuthorityModel> searchUserGranted(Authentication userInfo) throws Exception {
		return new ArrayList<UserGrantedAuthorityModel>();
	}

	/**
	 * 取得权限用户
	 */
	public List<String> searchUserInfo(String args) throws Exception {
		UserInfoModel userInfo = new UserInfoModel();
		userInfo.setUserId(args);
		userInfo = userInfoService.findUserInfo(userInfo);

		List<String> rtnList = new ArrayList<String>();
		if (userInfo != null) {
			rtnList.add(userInfo.getUserId());
		}
		return rtnList;
	}
}
