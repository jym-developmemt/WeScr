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
 * 页面项目行信息模型
 *
 * @author cp0
 * @since 3.0
 */
public class PageElementRowModel extends BaseModel {

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

	/** 行属性 */
	private String rowProp;

	/** 项目明细信息 */
	private List<PageElementCellModel> cellList = new ArrayList<PageElementCellModel>();

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
	 * Getting method of cellList
	 *
	 * @return cellList
	 */
	public List<PageElementCellModel> getCellList() {
		return cellList;
	}

	/**
	 * Setting method of cellList
	 *
	 * @param cellList
	 */
	public void setCellList(List<PageElementCellModel> cellList) {
		this.cellList = cellList;
	}
}
