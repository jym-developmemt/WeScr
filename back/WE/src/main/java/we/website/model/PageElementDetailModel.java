/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import we.base.base.BaseModel;

/**
 * 页面项目明细信息模型
 *
 * @author cp0
 * @since 3.0
 */
public class PageElementDetailModel extends BaseModel {

	/** 页面组ID */
	@JsonIgnore
	private String groupId;

	/** 页面ID */
	@JsonIgnore
	private String pageId;

	/** 项目番号 */
	private String elementIndex;

	/** 明细番号 */
	private String detailIndex;

	/** 权限类型 */
	@JsonIgnore
	private String authType;

	/** 权限值 */
	@JsonIgnore
	private String authValue;

	/** 项目名称 */
	private String detailName;

	/** 明细项目参数 */
	private String detailProp;

	/** 表示方式 */
	private String displayType;

	/** 表示方式数据 */
	private String displayData;

	/** 表示方式参数 */
	private String displayProp;

	/** 明细附加信息 */
	private String detailAddon;

	/**
	 * Getting method of groupId
	 *
	 * @return groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * Setting method of groupId
	 *
	 * @param groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * Getting method of pageId
	 *
	 * @return pageId
	 */
	public String getPageId() {
		return pageId;
	}

	/**
	 * Setting method of pageId
	 *
	 * @param pageId
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	/**
	 * Getting method of elementIndex
	 *
	 * @return elementIndex
	 */
	public String getElementIndex() {
		return elementIndex;
	}

	/**
	 * Setting method of elementIndex
	 *
	 * @param elementIndex
	 */
	public void setElementIndex(String elementIndex) {
		this.elementIndex = elementIndex;
	}

	/**
	 * Getting method of detailIndex
	 *
	 * @return detailIndex
	 */
	public String getDetailIndex() {
		return detailIndex;
	}

	/**
	 * Setting method of detailIndex
	 *
	 * @param detailIndex
	 */
	public void setDetailIndex(String detailIndex) {
		this.detailIndex = detailIndex;
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
	 * Getting method of detailName
	 *
	 * @return detailName
	 */
	public String getDetailName() {
		return detailName;
	}

	/**
	 * Setting method of detailName
	 *
	 * @param detailName
	 */
	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	/**
	 * Getting method of detailProp
	 *
	 * @return detailProp
	 */
	public String getDetailProp() {
		return detailProp;
	}

	/**
	 * Setting method of detailProp
	 *
	 * @param detailProp
	 */
	public void setDetailProp(String detailProp) {
		this.detailProp = detailProp;
	}

	/**
	 * Getting method of displayType
	 *
	 * @return displayType
	 */
	public String getDisplayType() {
		return displayType;
	}

	/**
	 * Setting method of displayType
	 *
	 * @param displayType
	 */
	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	/**
	 * Getting method of displayData
	 *
	 * @return displayData
	 */
	public String getDisplayData() {
		return displayData;
	}

	/**
	 * Setting method of displayData
	 *
	 * @param displayData
	 */
	public void setDisplayData(String displayData) {
		this.displayData = displayData;
	}

	/**
	 * Getting method of displayProp
	 *
	 * @return displayProp
	 */
	public String getDisplayProp() {
		return displayProp;
	}

	/**
	 * Setting method of displayProp
	 *
	 * @param displayProp
	 */
	public void setDisplayProp(String displayProp) {
		this.displayProp = displayProp;
	}

	/**
	 * Getting method of detailAddon
	 *
	 * @return detailAddon
	 */
	public String getDetailAddon() {
		return detailAddon;
	}

	/**
	 * Setting method of detailAddon
	 *
	 * @param detailAddon
	 */
	public void setDetailAddon(String detailAddon) {
		this.detailAddon = detailAddon;
	}

}
