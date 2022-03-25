/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import we.base.base.BaseModel;

/**
 * 职位信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class PositionInfoModel extends BaseModel {

	/** 版本ID */
	private int versionId;

	/** 职位ID */
	private String positionId;

	/** 职位编号 */
	private String positionNo;

	/** 职位名称 */
	private String positionName;

	/** 职位略名 */
	private String shortName;

	/** 上级职位ID */
	private String parentId;

	/** 职位阶层 */
	private String positionRank;

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
	 * Getting method of positionId
	 *
	 * @return positionId
	 */
	public String getPositionId() {
		return positionId;
	}

	/**
	 * Setting method of positionId
	 *
	 * @param positionId
	 */
	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

	/**
	 * Getting method of positionNo
	 *
	 * @return positionNo
	 */
	public String getPositionNo() {
		return positionNo;
	}

	/**
	 * Setting method of positionNo
	 *
	 * @param positionNo
	 */
	public void setPositionNo(String positionNo) {
		this.positionNo = positionNo;
	}

	/**
	 * Getting method of positionName
	 *
	 * @return positionName
	 */
	public String getPositionName() {
		return positionName;
	}

	/**
	 * Setting method of positionName
	 *
	 * @param positionName
	 */
	public void setPositionName(String positionName) {
		this.positionName = positionName;
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
	 * Getting method of positionRank
	 *
	 * @return positionRank
	 */
	public String getPositionRank() {
		return positionRank;
	}

	/**
	 * Setting method of positionRank
	 *
	 * @param positionRank
	 */
	public void setPositionRank(String positionRank) {
		this.positionRank = positionRank;
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
