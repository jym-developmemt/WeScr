/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;
import we.base.util.VersionHolder;
import we.core.CoreProperties;
import we.website.dao.UserInfoDao;
import we.website.model.UserInfoModel;
import we.website.service.UserInfoService;

/**
 * 用户信息Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class UserInfoServiceImpl extends BaseService implements UserInfoService {

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private VersionHolder versionHolder;

	/**
	 * 用户信息取得
	 */
	@Override
	public UserInfoModel findUserInfo(UserInfoModel userInfo) {
		try {
			// 超级管理员认证
			if (CommonUtil.equals(userInfo.getUserId(), websiteProp.getAdminId())
					|| CommonUtil.equals(userInfo.getLoginUserId(), websiteProp.getAdminId())) {
				UserInfoModel userModel = new UserInfoModel();
				userModel.setVersionId(versionHolder.getLastVersion());
				userModel.setUserId(userInfo.getUserId());
				userModel.setPassword(EncryptorUtil.MD5(websiteProp.getAdminPassword()));
				userModel.setUserName("超级管理员");
				userModel.setStartDate(CommonUtil.getSystemDate("yyyy/MM/dd HH:mm:ss"));
				userModel.setSysadmin(true);
				return userModel;
			}

			userInfo.setVersionId(versionHolder.getLastVersion());
			return userInfoDao.findUserInfo(userInfo);
		} catch(Exception e) {
			return null;
		}
	}

	/**
	 * 追加用户信息
	 */
	@Override
	public int addUserInfo(UserInfoModel userInfo) {
		userInfo.setPassword(EncryptorUtil.MD5(userInfo.getPassword()));
		return userInfoDao.addUserInfo(userInfo);
	}

	/**
	 * 更新用户信息
	 */
	@Override
	public int updateUserInfo(UserInfoModel userInfo) {
		userInfo.setPassword(EncryptorUtil.MD5(userInfo.getPassword()));
		return userInfoDao.updateUserInfo(userInfo);
	}

	/**
	 * 删除用户信息
	 */
	@Override
	public int deleteUserInfo(UserInfoModel userInfo) {
		return userInfoDao.deleteUserInfo(userInfo);
	}

	/**
	 * 用户前回登陆时间更新
	 */
	@Override
	public int updateLoginDate(String userId) {
		return userInfoDao.updateLoginDate(userId);
	}
}
