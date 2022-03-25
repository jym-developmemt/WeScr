/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import org.springframework.stereotype.Service;

import we.website.model.UserInfoModel;

/**
 * 用户信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public interface UserInfoService {
	/**
	 * 用户信息取得
	 */
	public UserInfoModel findUserInfo(UserInfoModel userInfo);

	/**
	 * 追加用户信息
	 */
	public int addUserInfo(UserInfoModel userInfo);

	/**
	 * 更新用户信息
	 */
	public int updateUserInfo(UserInfoModel userInfo);

	/**
	 * 删除用户信息
	 */
	public int deleteUserInfo(UserInfoModel userInfo);

	/**
	 * 用户前回登陆时间更新
	 */
	public int updateLoginDate(String userId);
}
