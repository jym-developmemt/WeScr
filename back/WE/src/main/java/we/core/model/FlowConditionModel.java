/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.model;

import we.base.base.BaseModel;

/**
 * 审批流程条件模型
 *
 * @author cp0
 * @since 0.0
 */
public class FlowConditionModel extends BaseModel {

	/** 版本ID */
	private int versionId;

	/** 申请ID */
	private String applyId;

	/** 流程番号 */
	private int flowIndex;

	/** 条件番号 */
	private int conditionIndex;

	/** 条件名称 */
	private String conditionName;

	/** 条件ID */
	private String conditionId;

	/** 条件类型 */
	private String conditionType;

	/** 设定值 */
	private String conditionValue;

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
	 * Getting method of flowIndex
	 *
	 * @return flowIndex
	 */
	public int getFlowIndex() {
		return flowIndex;
	}

	/**
	 * Setting method of flowIndex
	 *
	 * @param flowIndex
	 */
	public void setFlowIndex(int flowIndex) {
		this.flowIndex = flowIndex;
	}

	/**
	 * Getting method of conditionIndex
	 *
	 * @return conditionIndex
	 */
	public int getConditionIndex() {
		return conditionIndex;
	}

	/**
	 * Setting method of conditionIndex
	 *
	 * @param conditionIndex
	 */
	public void setConditionIndex(int conditionIndex) {
		this.conditionIndex = conditionIndex;
	}

	/**
	 * Getting method of conditionName
	 *
	 * @return conditionName
	 */
	public String getConditionName() {
		return conditionName;
	}

	/**
	 * Setting method of conditionName
	 *
	 * @param conditionName
	 */
	public void setConditionName(String conditionName) {
		this.conditionName = conditionName;
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
	 * Getting method of conditionType
	 *
	 * @return conditionType
	 */
	public String getConditionType() {
		return conditionType;
	}

	/**
	 * Setting method of conditionType
	 *
	 * @param conditionType
	 */
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	/**
	 * Getting method of conditionValue
	 *
	 * @return conditionValue
	 */
	public String getConditionValue() {
		return conditionValue;
	}

	/**
	 * Setting method of conditionValue
	 *
	 * @param conditionValue
	 */
	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

}
