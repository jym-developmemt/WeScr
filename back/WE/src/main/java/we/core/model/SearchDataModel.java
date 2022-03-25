/********************************************************************************
 * Copyright (c) 2017 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.model;

import java.util.List;

import we.base.base.BaseModel;

/**
 * 简单查询的数据模型
 *
 * @author cp0
 * @since 0.0
 */
public class SearchDataModel extends BaseModel {

	/** 列信息列表 */
	private List<String> columnList;

	/** 表信息列表 */
	private List<String> tableList;

	/** 条件列表 */
	private List<String> conditionList;

	/** 排序信息列表 */
	private List<String> orderList;

	/**
	 * Getting method of columnList
	 *
	 * @return the columnList
	 */
	public List<String> getColumnList() {
		return columnList;
	}

	/**
	 * Setting method of columnList
	 *
	 * @param columnList
	 */
	public void setColumnList(List<String> columnList) {
		this.columnList = columnList;
	}

	/**
	 * Getting method of tableList
	 *
	 * @return the tableList
	 */
	public List<String> getTableList() {
		return tableList;
	}

	/**
	 * Setting method of tableList
	 *
	 * @param tableList
	 */
	public void setTableList(List<String> tableList) {
		this.tableList = tableList;
	}

	/**
	 * Getting method of conditionList
	 *
	 * @return the conditionList
	 */
	public List<String> getConditionList() {
		return conditionList;
	}

	/**
	 * Setting method of conditionList
	 *
	 * @param conditionList
	 */
	public void setConditionList(List<String> conditionList) {
		this.conditionList = conditionList;
	}

	/**
	 * Getting method of orderList
	 *
	 * @return the orderList
	 */
	public List<String> getOrderList() {
		return orderList;
	}

	/**
	 * Setting method of orderList
	 *
	 * @param orderList
	 */
	public void setOrderList(List<String> orderList) {
		this.orderList = orderList;
	}

}
