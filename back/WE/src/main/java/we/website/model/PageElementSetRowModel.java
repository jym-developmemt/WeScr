/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import we.base.base.BaseModel;
import we.base.util.CommonUtil;

/**
 * 页面项目组行信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class PageElementSetRowModel extends BaseModel {

	/** 页面组ID */
	@JsonIgnore
	private String groupId;

	/** 页面ID */
	@JsonIgnore
	private String pageId;

	/** 行番号 */
	private String rowIndex;

	/** 项目组类型 */
	private String setType;

	/** 行属性 */
	private String rowProp;

	/** 权限类型 */
	@JsonIgnore
	private String authType;

	/** 权限值 */
	@JsonIgnore
	private String authValue;

	/** 页面项目组信息 */
	private List<PageElementSetModel> elementSetList = new ArrayList<PageElementSetModel>();

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
	 * Getting method of key
	 *
	 * @return key
	 */
	public String getKey() {
		return CommonUtil.generateKey();
	}

	/**
	 * Getting method of rowIndex
	 *
	 * @return rowIndex
	 */
	public String getRowIndex() {
		return rowIndex;
	}

	/**
	 * Setting method of rowIndex
	 *
	 * @param rowIndex
	 */
	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}

	/**
	 * Getting method of setType
	 *
	 * @return setType
	 */
	public String getSetType() {
		return setType;
	}

	/**
	 * Setting method of setType
	 *
	 * @param setType
	 */
	public void setSetType(String setType) {
		this.setType = setType;
	}

	/**
	 * Getting method of rowProp
	 *
	 * @return rowProp
	 */
	public String getRowProp() {
		return rowProp;
	}

	/**
	 * Setting method of rowProp
	 *
	 * @param rowProp
	 */
	public void setRowProp(String rowProp) {
		this.rowProp = rowProp;
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
	 * Getting method of elementSetList
	 *
	 * @return elementSetList
	 */
	public List<PageElementSetModel> getElementSetList() {
		return elementSetList;
	}

	/**
	 * Setting method of elementSetList
	 *
	 * @param elementSetList
	 */
	public void setElementSetList(List<PageElementSetModel> elementSetList) {
		this.elementSetList = elementSetList;
	}
}
