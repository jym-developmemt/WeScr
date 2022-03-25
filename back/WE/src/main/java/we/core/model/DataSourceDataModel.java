/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import we.base.base.PaginationModel;
import we.website.model.DataSourceColumnModel;
import we.website.model.DataSourceLinkConditionModel;
import we.website.model.DataSourceLinkTableModel;
import we.website.model.PageDataSourceColumnModel;

/**
 * 数据源数据检索模型
 *
 * @author cp0
 * @since 3.0
 */
public class DataSourceDataModel extends PaginationModel {
	/** 表ID */
	private String tableName;

	/** 数据源番号 */
	private String datasourceIndex;

	/** 条件连接类型 */
	private String condConcatType = "AND";

	/** 关联表ID列表 */
	private List<DataSourceLinkTableModel> linkTableList = new ArrayList<DataSourceLinkTableModel>();

	/** 关联表ID列表 */
	private List<DataSourceLinkTableModel> existTableList = new ArrayList<DataSourceLinkTableModel>();

	/** 关联条件列表 */
	private Map<String, List<DataSourceLinkConditionModel>> linkConditionMap = new HashMap<String, List<DataSourceLinkConditionModel>>();

	/** 关联条件列表 */
	private Map<String, List<PageDataSourceColumnModel>> linkColumnConditionMap = new HashMap<String, List<PageDataSourceColumnModel>>();

	/** 关联输入条件列表 */
	private Map<String, List<PageDataSourceColumnModel>> linkColumnInputConditionMap = new HashMap<String, List<PageDataSourceColumnModel>>();

	/** 属性信息列表1 */
	private List<DataSourceColumnModel> columnList1;

	/** 列信息列表2 */
	private List<DataSourceColumnModel> columnList2;

	/** 列信息列表3 */
	private List<PageDataSourceColumnModel> columnList3;

	/** 数据 */
	private Map<String, String> dataParam = new HashMap<String, String>();

	/** 数据列表 */
	private List<Map<String, String>> dataParamList = new ArrayList<Map<String, String>>();

	/** 查询条件 */
	private Map<String, Object> conditionParam = new HashMap<String, Object>();

	/**
	 * Getting method of tableName
	 *
	 * @return tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * Setting method of tableName
	 *
	 * @param tableName
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * Getting method of datasourceIndex
	 *
	 * @return datasourceIndex
	 */
	public String getDatasourceIndex() {
		return datasourceIndex;
	}

	/**
	 * Setting method of datasourceIndex
	 *
	 * @param datasourceIndex
	 */
	public void setDatasourceIndex(String datasourceIndex) {
		this.datasourceIndex = datasourceIndex;
	}

	/**
	 * Getting method of condConcatType
	 *
	 * @return condConcatType
	 */
	public String getCondConcatType() {
		return condConcatType;
	}

	/**
	 * Setting method of condConcatType
	 *
	 * @param condConcatType
	 */
	public void setCondConcatType(String condConcatType) {
		this.condConcatType = condConcatType;
	}

	/**
	 * Getting method of linkTableList
	 *
	 * @return linkTableList
	 */
	public List<DataSourceLinkTableModel> getLinkTableList() {
		return linkTableList;
	}

	/**
	 * Setting method of linkTableList
	 *
	 * @param linkTableList
	 */
	public void setLinkTableList(List<DataSourceLinkTableModel> linkTableList) {
		this.linkTableList = linkTableList;
	}

	/**
	 * Getting method of existTableList
	 *
	 * @return existTableList
	 */
	public List<DataSourceLinkTableModel> getExistTableList() {
		return existTableList;
	}

	/**
	 * Setting method of existTableList
	 *
	 * @param existTableList
	 */
	public void setExistTableList(List<DataSourceLinkTableModel> existTableList) {
		this.existTableList = existTableList;
	}

	/**
	 * Getting method of linkConditionList
	 *
	 * @return linkConditionList
	 */
	public Map<String, List<DataSourceLinkConditionModel>> getLinkConditionMap() {
		return linkConditionMap;
	}

	/**
	 * Setting method of linkConditionList
	 *
	 * @param linkConditionList
	 */
	public void setLinkConditionMap(Map<String, List<DataSourceLinkConditionModel>> linkConditionMap) {
		this.linkConditionMap = linkConditionMap;
	}

	/**
	 * Getting method of linkColumnConditionMap
	 *
	 * @return linkColumnConditionMap
	 */
	public Map<String, List<PageDataSourceColumnModel>> getLinkColumnConditionMap() {
		return linkColumnConditionMap;
	}

	/**
	 * Setting method of linkColumnConditionMap
	 *
	 * @param linkColumnConditionMap
	 */
	public void setLinkColumnConditionMap(Map<String, List<PageDataSourceColumnModel>> linkColumnConditionMap) {
		this.linkColumnConditionMap = linkColumnConditionMap;
	}

	/**
	 * Getting method of linkColumnInputConditionMap
	 *
	 * @return linkColumnInputConditionMap
	 */
	public Map<String, List<PageDataSourceColumnModel>> getLinkColumnInputConditionMap() {
		return linkColumnInputConditionMap;
	}

	/**
	 * Setting method of linkColumnInputConditionMap
	 *
	 * @param linkColumnInputConditionMap
	 */
	public void setLinkColumnInputConditionMap(Map<String, List<PageDataSourceColumnModel>> linkColumnInputConditionMap) {
		this.linkColumnInputConditionMap = linkColumnInputConditionMap;
	}

	/**
	 * Getting method of columnList1
	 *
	 * @return columnList1
	 */
	public List<DataSourceColumnModel> getColumnList1() {
		return columnList1;
	}

	/**
	 * Setting method of columnList1
	 *
	 * @param columnList1
	 */
	public void setColumnList1(List<DataSourceColumnModel> columnList1) {
		this.columnList1 = columnList1;
	}

	/**
	 * Getting method of columnList2
	 *
	 * @return columnList2
	 */
	public List<DataSourceColumnModel> getColumnList2() {
		return columnList2;
	}

	/**
	 * Setting method of columnList2
	 *
	 * @param columnList2
	 */
	public void setColumnList2(List<DataSourceColumnModel> columnList2) {
		this.columnList2 = columnList2;
	}

	/**
	 * Getting method of columnList3
	 *
	 * @return columnList3
	 */
	public List<PageDataSourceColumnModel> getColumnList3() {
		return columnList3;
	}

	/**
	 * Setting method of columnList3
	 *
	 * @param columnList3
	 */
	public void setColumnList3(List<PageDataSourceColumnModel> columnList3) {
		this.columnList3 = columnList3;
	}

	/**
	 * Getting method of dataParam
	 *
	 * @return dataParam
	 */
	public Map<String, String> getDataParam() {
		return dataParam;
	}

	/**
	 * Setting method of dataParam
	 *
	 * @param dataParam
	 */
	public void setDataParam(Map<String, String> dataParam) {
		this.dataParam = dataParam;
	}

	/**
	 * Getting method of dataParamList
	 *
	 * @return dataParamList
	 */
	public List<Map<String, String>> getDataParamList() {
		return dataParamList;
	}

	/**
	 * Setting method of dataParamList
	 *
	 * @param dataParamList
	 */
	public void setDataParamList(List<Map<String, String>> dataParamList) {
		this.dataParamList = dataParamList;
	}

	/**
	 * Getting method of conditionParam
	 *
	 * @return conditionParam
	 */
	public Map<String, Object> getConditionParam() {
		return conditionParam;
	}

	/**
	 * Setting method of conditionParam
	 *
	 * @param conditionParam
	 */
	public void setConditionParam(Map<String, Object> conditionParam) {
		this.conditionParam = conditionParam;
	}

	/**
	 * Getting method of aliasName
	 *
	 * @return aliasName
	 */
	public String getAliasName() {
		return "d" + datasourceIndex;
	}

}
