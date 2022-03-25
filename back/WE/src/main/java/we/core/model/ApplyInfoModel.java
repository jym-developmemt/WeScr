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
public class ApplyInfoModel extends BaseModel {

	/** 版本ID */
	private int versionId;

	/** 申请ID */
	private String applyId;

	/** 申请名称 */
	private String applyName;

	/** 申请说明 */
	private String applyComment;

	/** 申请图标 */
	private String applyIcon;

	/** 审批通知区分 */
	private String noticeFlg;

	/** 标题表达式 */
	private String titleExpression;

	/** 说明表达式 */
	private String commentExpression;

	/** 申请页面ID */
	private String applyPageId;

	/** 修正页面ID */
	private String modifyPageId;

	/** 审批页面ID */
	private String approvalPageId;

	/** 手机申请页面ID */
	private String mobileApplyPageId;

	/** 手机修正页面ID */
	private String mobileModifyPageId;

	/** 手机审批页面ID */
	private String mobileApprovalPageId;

	/** 更新数据源ID */
	private String updateDatasourceId;

	/** 申请实例ID列番号 */
	private String instanceIdColumnIndex;

	/** 申请状态列番号 */
	private String statusColumnIndex;

	/** 可见用户ID */
	private String authUser;

	/** 可见用户名称 */
	private String authUserName;

	/** 可见部门ID */
	private String authDepartment;

	/** 可见部门名称 */
	private String authDepartmentName;

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
	 * Getting method of applyName
	 *
	 * @return applyName
	 */
	public String getApplyName() {
		return applyName;
	}

	/**
	 * Setting method of applyName
	 *
	 * @param applyName
	 */
	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	/**
	 * Getting method of applyComment
	 *
	 * @return applyComment
	 */
	public String getApplyComment() {
		return applyComment;
	}

	/**
	 * Setting method of applyComment
	 *
	 * @param applyComment
	 */
	public void setApplyComment(String applyComment) {
		this.applyComment = applyComment;
	}

	/**
	 * Getting method of applyIcon
	 *
	 * @return applyIcon
	 */
	public String getApplyIcon() {
		return applyIcon;
	}

	/**
	 * Setting method of applyIcon
	 *
	 * @param applyIcon
	 */
	public void setApplyIcon(String applyIcon) {
		this.applyIcon = applyIcon;
	}

	/**
	 * Getting method of noticeFlg
	 *
	 * @return noticeFlg
	 */
	public String getNoticeFlg() {
		return noticeFlg;
	}

	/**
	 * Setting method of noticeFlg
	 *
	 * @param noticeFlg
	 */
	public void setNoticeFlg(String noticeFlg) {
		this.noticeFlg = noticeFlg;
	}

	/**
	 * Getting method of titleExpression
	 *
	 * @return titleExpression
	 */
	public String getTitleExpression() {
		return titleExpression;
	}

	/**
	 * Setting method of titleExpression
	 *
	 * @param titleExpression
	 */
	public void setTitleExpression(String titleExpression) {
		this.titleExpression = titleExpression;
	}

	/**
	 * Getting method of commentExpression
	 *
	 * @return commentExpression
	 */
	public String getCommentExpression() {
		return commentExpression;
	}

	/**
	 * Setting method of commentExpression
	 *
	 * @param commentExpression
	 */
	public void setCommentExpression(String commentExpression) {
		this.commentExpression = commentExpression;
	}

	/**
	 * Getting method of applyPageId
	 *
	 * @return applyPageId
	 */
	public String getApplyPageId() {
		return applyPageId;
	}

	/**
	 * Setting method of applyPageId
	 *
	 * @param applyPageId
	 */
	public void setApplyPageId(String applyPageId) {
		this.applyPageId = applyPageId;
	}

	/**
	 * Getting method of modifyPageId
	 *
	 * @return modifyPageId
	 */
	public String getModifyPageId() {
		return modifyPageId;
	}

	/**
	 * Setting method of modifyPageId
	 *
	 * @param modifyPageId
	 */
	public void setModifyPageId(String modifyPageId) {
		this.modifyPageId = modifyPageId;
	}

	/**
	 * Getting method of approvalPageId
	 *
	 * @return approvalPageId
	 */
	public String getApprovalPageId() {
		return approvalPageId;
	}

	/**
	 * Setting method of approvalPageId
	 *
	 * @param approvalPageId
	 */
	public void setApprovalPageId(String approvalPageId) {
		this.approvalPageId = approvalPageId;
	}

	/**
	 * Getting method of mobileApplyPageId
	 *
	 * @return mobileApplyPageId
	 */
	public String getMobileApplyPageId() {
		return mobileApplyPageId;
	}

	/**
	 * Setting method of mobileApplyPageId
	 *
	 * @param mobileApplyPageId
	 */
	public void setMobileApplyPageId(String mobileApplyPageId) {
		this.mobileApplyPageId = mobileApplyPageId;
	}

	/**
	 * Getting method of mobileModifyPageId
	 *
	 * @return mobileModifyPageId
	 */
	public String getMobileModifyPageId() {
		return mobileModifyPageId;
	}

	/**
	 * Setting method of mobileModifyPageId
	 *
	 * @param mobileModifyPageId
	 */
	public void setMobileModifyPageId(String mobileModifyPageId) {
		this.mobileModifyPageId = mobileModifyPageId;
	}

	/**
	 * Getting method of mobileApprovalPageId
	 *
	 * @return mobileApprovalPageId
	 */
	public String getMobileApprovalPageId() {
		return mobileApprovalPageId;
	}

	/**
	 * Setting method of mobileApprovalPageId
	 *
	 * @param mobileApprovalPageId
	 */
	public void setMobileApprovalPageId(String mobileApprovalPageId) {
		this.mobileApprovalPageId = mobileApprovalPageId;
	}

	/**
	 * Getting method of updateDatasourceId
	 *
	 * @return updateDatasourceId
	 */
	public String getUpdateDatasourceId() {
		return updateDatasourceId;
	}

	/**
	 * Setting method of updateDatasourceId
	 *
	 * @param updateDatasourceId
	 */
	public void setUpdateDatasourceId(String updateDatasourceId) {
		this.updateDatasourceId = updateDatasourceId;
	}

	/**
	 * Getting method of instanceIdColumnIndex
	 *
	 * @return instanceIdColumnIndex
	 */
	public String getInstanceIdColumnIndex() {
		return instanceIdColumnIndex;
	}

	/**
	 * Setting method of instanceIdColumnIndex
	 *
	 * @param instanceIdColumnIndex
	 */
	public void setInstanceIdColumnIndex(String instanceIdColumnIndex) {
		this.instanceIdColumnIndex = instanceIdColumnIndex;
	}

	/**
	 * Getting method of statusColumnIndex
	 *
	 * @return statusColumnIndex
	 */
	public String getStatusColumnIndex() {
		return statusColumnIndex;
	}

	/**
	 * Setting method of statusColumnIndex
	 *
	 * @param statusColumnIndex
	 */
	public void setStatusColumnIndex(String statusColumnIndex) {
		this.statusColumnIndex = statusColumnIndex;
	}

	/**
	 * Getting method of authUser
	 *
	 * @return authUser
	 */
	public String getAuthUser() {
		return authUser;
	}

	/**
	 * Setting method of authUser
	 *
	 * @param authUser
	 */
	public void setAuthUser(String authUser) {
		this.authUser = authUser;
	}

	/**
	 * Getting method of authUserName
	 *
	 * @return authUserName
	 */
	public String getAuthUserName() {
		return authUserName;
	}

	/**
	 * Setting method of authUserName
	 *
	 * @param authUserName
	 */
	public void setAuthUserName(String authUserName) {
		this.authUserName = authUserName;
	}

	/**
	 * Getting method of authDepartment
	 *
	 * @return authDepartment
	 */
	public String getAuthDepartment() {
		return authDepartment;
	}

	/**
	 * Setting method of authDepartment
	 *
	 * @param authDepartment
	 */
	public void setAuthDepartment(String authDepartment) {
		this.authDepartment = authDepartment;
	}

	/**
	 * Getting method of authDepartmentName
	 *
	 * @return authDepartmentName
	 */
	public String getAuthDepartmentName() {
		return authDepartmentName;
	}

	/**
	 * Setting method of authDepartmentName
	 *
	 * @param authDepartmentName
	 */
	public void setAuthDepartmentName(String authDepartmentName) {
		this.authDepartmentName = authDepartmentName;
	}
}
