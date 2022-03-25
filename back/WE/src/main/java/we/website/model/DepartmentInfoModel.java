/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import we.base.base.BaseModel;

/**
 * 部门信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class DepartmentInfoModel extends BaseModel {

	/** 版本ID */
	private int versionId;

	/** 部门ID */
	private String departmentId;

	/** 部门编号 */
	private String departmentNo;

	/** 部门名称 */
	private String departmentName;

	/** 部门略名 */
	private String shortName;

	/** 上级部门ID */
	private String parentId;

	/** 上级部门ID */
	private String parentIdPrefix;

	/** 上级部门ID */
	private String parentIdSuffix;

	/** 部门阶层 */
	private String departmentRank;

	/** 附加信息1 */
	private String addon1;

	/** 附加信息2 */
	private String addon2;

	/** 附加信息3 */
	private String addon3;

	/** 附加信息4 */
	private String addon4;

	/** 附加信息5 */
	private String addon5;

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
	 * Getting method of departmentNo
	 *
	 * @return departmentNo
	 */
	public String getDepartmentNo() {
		return departmentNo;
	}

	/**
	 * Setting method of departmentNo
	 *
	 * @param departmentNo
	 */
	public void setDepartmentNo(String departmentNo) {
		this.departmentNo = departmentNo;
	}

	/**
	 * Getting method of departmentName
	 *
	 * @return departmentName
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * Setting method of departmentName
	 *
	 * @param departmentName
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * Getting method of shortName
	 *
	 * @return shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * Setting method of shortName
	 *
	 * @param shortName
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * Getting method of parentId
	 *
	 * @return parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Setting method of parentId
	 *
	 * @param parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Getting method of parentIdPrefix
	 *
	 * @return parentIdPrefix
	 */
	public String getParentIdPrefix() {
		return parentIdPrefix;
	}

	/**
	 * Setting method of parentIdPrefix
	 *
	 * @param parentIdPrefix
	 */
	public void setParentIdPrefix(String parentIdPrefix) {
		this.parentIdPrefix = parentIdPrefix;
	}

	/**
	 * Getting method of parentIdSuffix
	 *
	 * @return parentIdSuffix
	 */
	public String getParentIdSuffix() {
		return parentIdSuffix;
	}

	/**
	 * Setting method of parentIdSuffix
	 *
	 * @param parentIdSuffix
	 */
	public void setParentIdSuffix(String parentIdSuffix) {
		this.parentIdSuffix = parentIdSuffix;
	}

	/**
	 * Getting method of departmentRank
	 *
	 * @return departmentRank
	 */
	public String getDepartmentRank() {
		return departmentRank;
	}

	/**
	 * Setting method of departmentRank
	 *
	 * @param departmentRank
	 */
	public void setDepartmentRank(String departmentRank) {
		this.departmentRank = departmentRank;
	}

	/**
	 * Getting method of addon1
	 *
	 * @return addon1
	 */
	public String getAddon1() {
		return addon1;
	}

	/**
	 * Setting method of addon1
	 *
	 * @param addon1
	 */
	public void setAddon1(String addon1) {
		this.addon1 = addon1;
	}

	/**
	 * Getting method of addon2
	 *
	 * @return addon2
	 */
	public String getAddon2() {
		return addon2;
	}

	/**
	 * Setting method of addon2
	 *
	 * @param addon2
	 */
	public void setAddon2(String addon2) {
		this.addon2 = addon2;
	}

	/**
	 * Getting method of addon3
	 *
	 * @return addon3
	 */
	public String getAddon3() {
		return addon3;
	}

	/**
	 * Setting method of addon3
	 *
	 * @param addon3
	 */
	public void setAddon3(String addon3) {
		this.addon3 = addon3;
	}

	/**
	 * Getting method of addon4
	 *
	 * @return addon4
	 */
	public String getAddon4() {
		return addon4;
	}

	/**
	 * Setting method of addon4
	 *
	 * @param addon4
	 */
	public void setAddon4(String addon4) {
		this.addon4 = addon4;
	}

	/**
	 * Getting method of addon5
	 *
	 * @return addon5
	 */
	public String getAddon5() {
		return addon5;
	}

	/**
	 * Setting method of addon5
	 *
	 * @param addon5
	 */
	public void setAddon5(String addon5) {
		this.addon5 = addon5;
	}

}
