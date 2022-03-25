/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import org.apache.commons.beanutils.PropertyUtils;

import we.base.base.BaseModel;
import we.base.util.CommonUtil;

/**
 * 数据源列信息模型
 *
 * @author cp0
 * @since 3.0
 */
public class DataSourceColumnModel extends BaseModel {

	/** 数据源ID */
	private String datasourceId;

	/** 列番号 */
	private String columnIndex;

	/** 列ID */
	private String columnId;

	/** 表示名称 */
	private String displayName;

	/** 主键区分 */
	private String primaryKey;

	/** 非空区分 */
	private String notnull;

	/**
	 * Getting method of datasourceId
	 *
	 * @return datasourceId
	 */
	public String getDatasourceId() {
		return datasourceId;
	}

	/**
	 * Setting method of datasourceId
	 *
	 * @param datasourceId
	 */
	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	/**
	 * Getting method of columnIndex
	 *
	 * @return columnIndex
	 */
	public String getColumnIndex() {
		return columnIndex;
	}

	/**
	 * Setting method of columnIndex
	 *
	 * @param columnIndex
	 */
	public void setColumnIndex(String columnIndex) {
		this.columnIndex = columnIndex;
	}

	/**
	 * Getting method of columnId
	 *
	 * @return columnId
	 */
	public String getColumnId() {
		return columnId;
	}

	/**
	 * Setting method of columnId
	 *
	 * @param columnId
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	/**
	 * Getting method of displayName
	 *
	 * @return displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Setting method of displayName
	 *
	 * @param displayName
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Getting method of primaryKey
	 *
	 * @return primaryKey
	 */
	public String getPrimaryKey() {
		return primaryKey;
	}

	/**
	 * Setting method of primaryKey
	 *
	 * @param primaryKey
	 */
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * Getting method of referKey
	 *
	 * @param index
	 * @return referKey
	 */
	public String getReferKey(String index) throws Exception {
		return CommonUtil.toString(PropertyUtils.getProperty(this, "referKey" + index));
	}

	/**
	 * Getting method of notnull
	 *
	 * @return notnull
	 */
	public String getNotnull() {
		return notnull;
	}

	/**
	 * Setting method of notnull
	 *
	 * @param notnull
	 */
	public void setNotnull(String notnull) {
		this.notnull = notnull;
	}
}
