/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.model;

import we.core.auth.AuthGrantorType;

/**
 * 人员功能信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class FunctionDepartmentModel extends UserGrantedAuthorityModel {

	/** 版本ID */
	private int versionId;

	/** 用户ID */
	private String userId;

	/** 角色ID */
	private String roleId;

	/** 功能ID */
	private String functionId;

	/** 部门ID */
	private String departmentId;

	/** 管理类型 */
	private String manageType;

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
	 * Getting method of functionId
	 *
	 * @return functionId
	 */
	public String getFunctionId() {
		return functionId;
	}

	/**
	 * Setting method of functionId
	 *
	 * @param functionId
	 */
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	/**
	 * Getting method of departmentId
	 *
	 * @return departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * Setting method of departmentId
	 *
	 * @param departmentId
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * Getting method of manageType
	 *
	 * @return manageType
	 */
	public String getManageType() {
		return manageType;
	}

	/**
	 * Setting method of manageType
	 *
	 * @param manageType
	 */
	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	/**
	 * 取得权限类型
	 */
	@Override
	public String getType() {
		return AuthGrantorType.USER_MANAGE_DEPARTMENT;
	}

	/**
	 * 取得权限值
	 */
	@Override
	public String getValue() {
		return this.functionId + ":" + this.departmentId;
	}
}
