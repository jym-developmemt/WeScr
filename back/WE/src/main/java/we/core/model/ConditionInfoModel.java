/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.model;

import we.base.base.BaseModel;

/**
 * 流程条件信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class ConditionInfoModel extends BaseModel {

	/** 版本ID */
	private int versionId;

	/** 申请ID */
	private String applyId;

	/** 条件ID */
	private String conditionId;

	/** 项目名称 */
	private String itemName;

	/** 条件类型 */
	private String itemType;

	/** 条件项目KEY */
	private String itemKey;

	/** 可选值 */
	private String options;

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
	 * Getting method of applyId
	 *
	 * @return applyId
	 */
	public String getApplyId() {
		return applyId;
	}

	/**
	 * Setting method of applyId
	 *
	 * @param applyId
	 */
	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	/**
	 * Getting method of conditionId
	 *
	 * @return conditionId
	 */
	public String getConditionId() {
		return conditionId;
	}

	/**
	 * Setting method of conditionId
	 *
	 * @param conditionId
	 */
	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	/**
	 * Getting method of itemName
	 *
	 * @return itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Setting method of itemName
	 *
	 * @param itemName
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * Getting method of itemType
	 *
	 * @return itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * Setting method of itemType
	 *
	 * @param itemType
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	/**
	 * Getting method of itemKey
	 *
	 * @return itemKey
	 */
	public String getItemKey() {
		return itemKey;
	}

	/**
	 * Setting method of itemKey
	 *
	 * @param itemKey
	 */
	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	/**
	 * Getting method of options
	 *
	 * @return options
	 */
	public String getOptions() {
		return options;
	}

	/**
	 * Setting method of options
	 *
	 * @param options
	 */
	public void setOptions(String options) {
		this.options = options;
	}

}
