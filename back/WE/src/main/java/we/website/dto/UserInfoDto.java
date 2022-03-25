/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dto;

import we.base.dto.ReceiveDto;
import we.website.model.UserInfoModel;

/**
 * 用户信息Dto
 *
 * @author cp0
 * @since 0.0
 */
public class UserInfoDto extends ReceiveDto {

	/** 用户ID */
	private String userId;

	/** 用户登录用ID */
	private String loginUserId;

	/** 登陆密码 */
	private String password;

	/** 企业微信用户ID */
	private String qywxUserId;

	/** 企业微信OPENID */
	private String qywxOpenId;

	/** 用户信息 */
	private UserInfoModel userInfo;

	/**
	 * Getting method of userId
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Setting method of userId
	 *
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Getting method of loginUserId
	 *
	 * @return loginUserId
	 */
	public String getLoginUserId() {
		return loginUserId;
	}

	/**
	 * Setting method of loginUserId
	 *
	 * @param loginUserId
	 */
	public void setLoginUserId(String loginUserId) {
		this.loginUserId = loginUserId;
	}

	/**
	 * Getting method of password
	 *
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setting method of password
	 *
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Getting method of qywxUserId
	 *
	 * @return qywxUserId
	 */
	public String getQywxUserId() {
		return qywxUserId;
	}

	/**
	 * Setting method of qywxUserId
	 *
	 * @param qywxUserId
	 */
	public void setQywxUserId(String qywxUserId) {
		this.qywxUserId = qywxUserId;
	}

	/**
	 * Getting method of qywxOpenId
	 *
	 * @return qywxOpenId
	 */
	public String getQywxOpenId() {
		return qywxOpenId;
	}

	/**
	 * Setting method of qywxOpenId
	 *
	 * @param qywxOpenId
	 */
	public void setQywxOpenId(String qywxOpenId) {
		this.qywxOpenId = qywxOpenId;
	}

	/**
	 * Getting method of userInfo
	 *
	 * @return userInfo
	 */
	public UserInfoModel getUserInfo() {
		return userInfo;
	}

	/**
	 * Setting method of userInfo
	 *
	 * @param userInfo
	 */
	public void setUserInfo(UserInfoModel userInfo) {
		this.userInfo = userInfo;
	}

}
