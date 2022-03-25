/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import we.base.util.CommonUtil;
import we.core.CoreProperties;
import we.core.auth.CliendAuthService;
import we.website.dao.UserInfoDao;
import we.website.model.UserInfoModel;
import we.wechat.WxUserManager;

/**
 * 企业微信认证Service
 *
 * @author cp0
 * @since 5.0
 */
@Service
public class QywxAuthService implements CliendAuthService {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private WxUserManager wxUserManager;

	/**
	 * 加载用户信息
	 */
	@Override
	public UserDetails loadUserByUsername(String userCode) throws AuthenticationException {
		if (userCode == null) {
			return null;
		}

		UserInfoModel userInfo = null;

		try {
			// 获取企业微信用户信息
			JSONObject result = wxUserManager.getUserInfo(userCode);

			// 错误代码
			int errcode = result.getInt("errcode");
			if (errcode != 0) {
				return null;
			}

			// 根据用户ID取得用户信息
			if (result.has("UserId")) {
				// 用户信息取得
				UserInfoModel param = new UserInfoModel();
				param.setQywxUserId(result.getString("UserId"));
				userInfo = userInfoDao.findUserInfo(param);
//				if (userInfo == null) {
//					throw new QywxUserIdNotBindException(
//							EncryptorUtil.encryptString(result.getString("UserId"), websiteProp.getDesSalt()));
//				}
			}

			// 根据开放ID取得用户信息
			else if (result.has("OpenId")) {
				// 用户信息取得
				UserInfoModel param = new UserInfoModel();
				param.setQywxOpenId(result.getString("OpenId"));
				userInfo = userInfoDao.findUserInfo(param);
//				if (userInfo == null) {
//					throw new QywxOpenIdNotBindException(
//							EncryptorUtil.encryptString(result.getString("OpenId"), websiteProp.getDesSalt()));
//				}
			}

		} catch (Exception e) {
			logger.error("企业微信认证时发生错误", e);
			return null;
		}

		return userInfo;
	}

	/**
	 * ClientID支持校验
	 */
	@Override
	public boolean supports(String clientId) {
		// 是否开启企业微信认证
		if (websiteProp.isQyapiAuthority() == false) {
			return false;
		}

		// 企业微信认证
		if (CommonUtil.equals(clientId, websiteProp.getClientIdQywx())) {
			return true;
		}
		return false;
	}

	/**
	 * 是否需要校验密码
	 */
	@Override
	public boolean checkPassword() {
		// 不需要校验密码
		return false;
	}
}
