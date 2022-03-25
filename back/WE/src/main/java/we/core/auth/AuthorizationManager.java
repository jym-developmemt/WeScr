/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import we.base.util.CommonUtil;
import we.core.CoreProperties;
import we.core.model.UserGrantedAuthorityModel;

/**
 * <b>权限管理器</b>
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class AuthorizationManager {

	@Autowired
	private CoreProperties coreProperties;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 检查当前登录用户是否有该权限
	 */
	public boolean hasAuthority(String type, String args) throws Exception {
		Authentication userInfo = SecurityContextHolder.getContext().getAuthentication();
		return this.hasAuthority(type, args, userInfo);
	}

	/**
	 * 检查用户是否有该权限
	 */
	public boolean hasAuthority(String type, String args, Authentication userInfo) throws Exception {
		if (userInfo == null) {
			userInfo = SecurityContextHolder.getContext().getAuthentication();
		}

		if (userInfo == null) {
			return false;
		}

		// 超级管理员
		if (CommonUtil.equals(coreProperties.getAdminId(), userInfo.getName())) {
			return true;
		}

		Map<String, AuthGrantor> authGrantorMap = applicationContext.getBeansOfType(AuthGrantor.class);
		for (AuthGrantor authGrantor : authGrantorMap.values()) {
			if (CommonUtil.equals(type, authGrantor.getAuthType())) {
				// 启动校验器
				return authGrantor.hasAuthority(userInfo, args);
			}
		}

		throw new Exception("Unkown Auth Grantor Type!");
	}

	/**
	 * 取得用户权限
	 */
	public List<UserGrantedAuthorityModel> getUserGranted(Authentication userInfo, String type) throws Exception {
		List<UserGrantedAuthorityModel> rtnList = new ArrayList<UserGrantedAuthorityModel>();

		if (userInfo == null) {
			return rtnList;
		}

		// 超级管理员
		if (CommonUtil.equals(coreProperties.getAdminId(), userInfo.getName())) {
			return rtnList;
		}

		Map<String, AuthGrantor> authGrantorMap = applicationContext.getBeansOfType(AuthGrantor.class);
		for (AuthGrantor authGrantor : authGrantorMap.values()) {
			if (type != null) {
				if (CommonUtil.equals(type, authGrantor.getAuthType())) {
					return authGrantor.searchUserGranted(userInfo);
				}
			} else {
				rtnList.addAll(authGrantor.searchUserGranted(userInfo));
			}
		}
		return rtnList;
	}

	/**
	 * 取得权限用户
	 */
	public List<String> getGrantedUsers(String type, String args) throws Exception {
		List<String> rtnList = new ArrayList<String>();

		Map<String, AuthGrantor> authGrantorMap = applicationContext.getBeansOfType(AuthGrantor.class);
		for (AuthGrantor authGrantor : authGrantorMap.values()) {
			if (type != null) {
				if (CommonUtil.equals(type, authGrantor.getAuthType())) {
					return authGrantor.searchUserInfo(args);
				}
			} else {
				rtnList.addAll(authGrantor.searchUserInfo(args));
			}
		}
		return rtnList.stream().distinct().collect(Collectors.toList());
	}
}
