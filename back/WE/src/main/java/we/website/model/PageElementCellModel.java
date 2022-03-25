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
 * 页面项目单元格信息模型
 *
 * @author cp0
 * @since 3.0
 */
public class PageElementCellModel extends BaseModel {

	/** 页面组ID */
	@JsonIgnore
	private String groupId;

	/** 页面ID */
	@JsonIgnore
	private String pageId;

	/** 项目组ID */
	@JsonIgnore
	private String setId;

	/** 行番号 */
	private String rowIndex;

	/** 列番号 */
	private String colIndex;

	/** 列属性 */
	private String colProp;

	/** 画面项目信息 */
	private List<PageElementBaseModel> elementList = new ArrayList<PageElementBaseModel>();

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
	 * Getting method of elementList
	 *
	 * @return elementList
	 */
	public List<PageElementBaseModel> getElementList() {
		return elementList;
	}

	/**
	 * Setting method of elementList
	 *
	 * @param elementList
	 */
	public void setElementList(List<PageElementBaseModel> elementList) {
		this.elementList = elementList;
	}
}
