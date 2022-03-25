/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;
import we.base.util.VersionHolder;
import we.core.CoreProperties;
import we.core.auth.CliendAuthService;
import we.website.dao.UserInfoDao;
import we.website.model.UserInfoModel;

/**
 * 站点信息认证Service
 *
 * @author cp0
 * @since 5.0
 */
@Service
public class SiteAuthService implements CliendAuthService {

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private VersionHolder versionHolder;

	/**
	 * 加载用户信息
	 */
	@Override
	public UserDetails loadUserByUsername(String userId) throws AuthenticationException {
		if (userId == null) {
			return null;
		}

		try {

			if (CommonUtil.equals(userId, websiteProp.getAdminId())) {
				// 超级管理员认证
				UserInfoModel userModel = new UserInfoModel();
				userModel.setVersionId(versionHolder.getLastVersion());
				userModel.setUserId(userId);
				userModel.setPassword(EncryptorUtil.MD5(websiteProp.getAdminPassword()));
				userModel.setUserName("超级管理员");
				userModel.setStartDate(CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss"));
				userModel.setSysadmin(true);
				return userModel;
			}

			// 用户信息取得
			UserInfoModel param = new UserInfoModel();
			param.setVersionId(versionHolder.getLastVersion());
			param.setLoginUserId(userId);
			return userInfoDao.findUserInfo(param);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * ClientID支持校验
	 */
	@Override
	public boolean supports(String clientId) {
		// 站点认证
		if (CommonUtil.equals(clientId, websiteProp.getClientIdSite())) {
			return true;
		}
		// 手机认证
		if (CommonUtil.equals(clientId, websiteProp.getClientIdMobile())) {
			return true;
		}
		return false;
	}

	/**
	 * 是否需要校验密码
	 */
	@Override
	public boolean checkPassword() {
		// 需要校验密码
		return true;
	}
}
