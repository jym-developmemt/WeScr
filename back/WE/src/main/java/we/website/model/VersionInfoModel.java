/********************************************************************************
 * Copyright (c) 2029 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import java.util.Date;

import we.base.base.BaseModel;

/**
 * 版本信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class VersionInfoModel extends BaseModel {

	/** 版本ID */
	private int versionId;

	/** 备考 */
	private String comment;

	/** 有效开始时间 */
	private Date startDate;

	/** 有效结束时间 */
	private Date endDate;

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
	 * Getting method of comment
	 *
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Setting method of comment
	 *
	 * @param comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Getting method of startDate
	 *
	 * @return startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * Setting method of startDate
	 *
	 * @param startDate
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Getting method of endDate
	 *
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * Setting method of endDate
	 *
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
