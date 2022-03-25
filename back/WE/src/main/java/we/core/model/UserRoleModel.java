/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.model;

import we.core.auth.AuthGrantorType;

/**
 * 人员角色信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class UserRoleModel extends UserGrantedAuthorityModel {

	/** 版本ID */
	private int versionId;

	/** 用户ID */
	private String userId;

	/** 角色ID */
	private String roleId;

	/**
	 * Getting method of versionId
	 *
	 * @return versionId
	 */
	public int getVersionId() {
		return versionId;
	}

	/**
	 * Setting method of versionId
	 *
	 * @param versionId
	 */
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

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
	 * Getting method of roleId
	 *
	 * @return roleId
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * Setting method of roleId
	 *
	 * @param roleId
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 取得权限类型
	 */
	@Override
	public String getType() {
		return AuthGrantorType.USER_ROLE;
	}

	/**
	 * 取得权限值
	 */
	@Override
	public String getValue() {
		return this.roleId;
	}
}
