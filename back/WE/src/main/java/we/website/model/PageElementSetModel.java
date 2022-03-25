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
 * 页面项目组信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class PageElementSetModel extends BaseModel {

	/** 页面组ID */
	@JsonIgnore
	private String groupId;

	/** 页面ID */
	@JsonIgnore
	private String pageId;

	/** 项目组ID */
	private String setId;

	/** 项目组名称 */
	private String setName;

	/** 项目组类型 */
	private String setType;

	/** 项目组图标 */
	private String setIcon;

	/** 项目组描述 */
	private String setComment;

	/** 行番号 */
	private String rowIndex;

	/** 行属性 */
	@JsonIgnore
	private String rowProp;

	/** 列番号 */
	private String colIndex;

	/** 列参数 */
	private String colProp;

	/** 权限类型 */
	@JsonIgnore
	private String authType;

	/** 权限值 */
	@JsonIgnore
	private String authValue;

	/** 隐藏标题 */
	private String hiddenTitle;

	/** 项目附加信息 */
	private String setAddon;

	/** 项目行信息 */
	private List<PageElementRowModel> elementRowList = new ArrayList<PageElementRowModel>();

	/**
	 * Getting method of key
	 *
	 * @return key
	 */
	public String getKey() {
		return CommonUtil.generateKey();
	}

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
	 * Getting method of setName
	 *
	 * @return setName
	 */
	public String getSetName() {
		return setName;
	}

	/**
	 * Setting method of setName
	 *
	 * @param setName
	 */
	public void setSetName(String setName) {
		this.setName = setName;
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
	 * Getting method of setIcon
	 *
	 * @return setIcon
	 */
	public String getSetIcon() {
		return setIcon;
	}

	/**
	 * Setting method of setIcon
	 *
	 * @param setIcon
	 */
	public void setSetIcon(String setIcon) {
		this.setIcon = setIcon;
	}

	/**
	 * Getting method of setComment
	 *
	 * @return setComment
	 */
	public String getSetComment() {
		return setComment;
	}

	/**
	 * Setting method of setComment
	 *
	 * @param setComment
	 */
	public void setSetComment(String setComment) {
		this.setComment = setComment;
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
	 * Getting method of hiddenTitle
	 *
	 * @return hiddenTitle
	 */
	public String getHiddenTitle() {
		return hiddenTitle;
	}

	/**
	 * Setting method of hiddenTitle
	 *
	 * @param hiddenTitle
	 */
	public void setHiddenTitle(String hiddenTitle) {
		this.hiddenTitle = hiddenTitle;
	}

	/**
	 * Getting method of setAddon
	 *
	 * @return setAddon
	 */
	public String getSetAddon() {
		return setAddon;
	}

	/**
	 * Setting method of setAddon
	 *
	 * @param setAddon
	 */
	public void setSetAddon(String setAddon) {
		this.setAddon = setAddon;
	}

	/**
	 * Getting method of elementRowList
	 *
	 * @return elementRowList
	 */
	public List<PageElementRowModel> getElementRowList() {
		return elementRowList;
	}

	/**
	 * Setting method of elementRowList
	 *
	 * @param elementRowList
	 */
	public void setElementRowList(List<PageElementRowModel> elementRowList) {
		this.elementRowList = elementRowList;
	}
}
