/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import we.base.base.BaseModel;

/**
 * 审批流程信息模型
 *
 * @author cp0
 * @since 0.0
 */
@JsonSerialize
public class ApplyFlowModel extends BaseModel {

	/** 版本ID */
	private int versionId;

	/** 申请ID */
	private String applyId;

	/** 流程番号 */
	private int flowIndex;

	/** 步骤番号 */
	private int stepIndex = -1;

	/** 步骤名称 */
	private String stepName;

	/** 审批方式 */
	private String approvalType;

	/** 权限类型 */
	private String authType;

	/** 权限值 */
	private String authValue;

	/** 权限名称 */
	private String authName;
	
	/** 超市审批flg */
	private String timeoutFlag;

	/** 超市审批有效时间 */
	private String timeoutDuetime;

	/** 审批去重flg */
	private String repeatFlag;

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
	 * Getting method of stepIndex
	 *
	 * @return stepIndex
	 */
	public int getStepIndex() {
		return stepIndex;
	}

	/**
	 * Setting method of stepIndex
	 *
	 * @param stepIndex
	 */
	public void setStepIndex(int stepIndex) {
		this.stepIndex = stepIndex;
	}

	/**
	 * Getting method of stepName
	 *
	 * @return stepName
	 */
	public String getStepName() {
		return stepName;
	}

	/**
	 * Setting method of stepName
	 *
	 * @param stepName
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	/**
	 * Getting method of approvalType
	 *
	 * @return approvalType
	 */
	public String getApprovalType() {
		return approvalType;
	}

	/**
	 * Setting method of approvalType
	 *
	 * @param approvalType
	 */
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	/**
	 * Getting method of authType
	 *
	 * @return authType
	 */
	public String getAuthType() {
		return authType;
	}

	/**
	 * Setting method of authType
	 *
	 * @param authType
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}

	/**
	 * Getting method of authValue
	 *
	 * @return authValue
	 */
	public String getAuthValue() {
		return authValue;
	}

	/**
	 * Setting method of authValue
	 *
	 * @param authValue
	 */
	public void setAuthValue(String authValue) {
		this.authValue = authValue;
	}

	/**
	 * Getting method of authName
	 *
	 * @return authName
	 */
	public String getAuthName() {
		return authName;
	}

	/**
	 * Setting method of authName
	 *
	 * @param authName
	 */
	public void setAuthName(String authName) {
		this.authName = authName;
	}
	
	/**
	 * Getting method of timeoutFlag
	 *
	 * @return timeoutFlag
	 */
	public String getTimeoutFlag() {
		return timeoutFlag;
	}

	/**
	 * Setting method of timeoutFlag
	 *
	 * @param timeoutFlag
	 */
	public void setTimeoutFlag(String timeoutFlag) {
		this.timeoutFlag = timeoutFlag;
	}

	/**
	 * Getting method of timeoutDuetime
	 *
	 * @return timeoutDuetime
	 */
	public String getTimeoutDuetime() {
		return timeoutDuetime;
	}

	/**
	 * Setting method of timeoutDuetime
	 *
	 * @param timeoutDuetime
	 */
	public void setTimeoutDuetime(String timeoutDuetime) {
		this.timeoutDuetime = timeoutDuetime;
	}

	
	/**
	 * Getting method of repeatFlag
	 *
	 * @return repeatFlag
	 */
	public String getRepeatFlag() {
		return repeatFlag;
	}

	/**
	 * Setting method of repeatFlag
	 *
	 * @param repeatFlag
	 */
	public void setRepeatFlag(String RepeatFlag) {
		this.repeatFlag = RepeatFlag;
	}


}
