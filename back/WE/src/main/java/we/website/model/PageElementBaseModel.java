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

/**
 * 页面项目基本信息模型
 *
 * @author cp0
 * @since 3.0
 */
public class PageElementBaseModel extends BaseModel {

	/** 页面组ID */
	@JsonIgnore
	private String groupId;

	/** 页面ID */
	@JsonIgnore
	private String pageId;

	/** 项目番号 */
	private String elementIndex;

	/** 项目组ID */
	@JsonIgnore
	private String setId;

	/** 行番号 */
	private String rowIndex;

	/** 行属性 */
	@JsonIgnore
	private String rowProp;

	/** 列番号 */
	private String colIndex;

	/** 列属性 */
	@JsonIgnore
	private String colProp;

	/** 权限类型 */
	@JsonIgnore
	private String authType;

	/** 权限值 */
	@JsonIgnore
	private String authValue;

	/** 表示方式 */
	private String displayType;

	/** 表示方式数据 */
	private String displayData;

	/** 表示方式参数 */
	private String displayProp;

	/** 项目附加信息 */
	private String elementAddon;

	/** 项目明细信息 */
	private List<PageElementDetailModel> detailList = new ArrayList<PageElementDetailModel>();

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
	 * Getting method of setId
	 *
	 * @return setId
	 */
	public String getSetId() {
		return setId;
	}

	/**
	 * Setting method of setId
	 *
	 * @param setId
	 */
	public void setSetId(String setId) {
		this.setId = setId;
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
	 * Getting method of colIndex
	 *
	 * @return colIndex
	 */
	public String getColIndex() {
		return colIndex;
	}

	/**
	 * Setting method of colIndex
	 *
	 * @param colIndex
	 */
	public void setColIndex(String colIndex) {
		this.colIndex = colIndex;
	}

	/**
	 * Getting method of colProp
	 *
	 * @return colProp
	 */
	public String getColProp() {
		return colProp;
	}

	/**
	 * Setting method of colProp
	 *
	 * @param colProp
	 */
	public void setColProp(String colProp) {
		this.colProp = colProp;
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
	 * Getting method of elementAddon
	 *
	 * @return elementAddon
	 */
	public String getElementAddon() {
		return elementAddon;
	}

	/**
	 * Setting method of elementAddon
	 *
	 * @param elementAddon
	 */
	public void setElementAddon(String elementAddon) {
		this.elementAddon = elementAddon;
	}

	/**
	 * Getting method of detailList
	 *
	 * @return detailList
	 */
	public List<PageElementDetailModel> getDetailList() {
		return detailList;
	}

	/**
	 * Setting method of detailList
	 *
	 * @param detailList
	 */
	public void setDetailList(List<PageElementDetailModel> detailList) {
		this.detailList = detailList;
	}
}
