/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import we.base.base.BaseModel;

/**
 * 数据源信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class DataSourceLinkTableModel extends BaseModel {

	/** 对象名称 */
	private String tableName;

	/** 对象别名 */
	private String aliasName;

	/** 关联方式 */
	private String joinType = "INNER";

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
	 * Getting method of aliasName
	 *
	 * @return aliasName
	 */
	public String getAliasName() {
		return aliasName;
	}

	/**
	 * Setting method of aliasName
	 *
	 * @param aliasName
	 */
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}

	/**
	 * Getting method of joinType
	 *
	 * @return joinType
	 */
	public String getJoinType() {
		return joinType;
	}

	/**
	 * Setting method of joinType
	 *
	 * @param joinType
	 */
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

}
