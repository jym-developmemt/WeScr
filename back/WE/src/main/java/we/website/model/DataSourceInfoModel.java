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
 * @since 3.0
 */
public class DataSourceInfoModel extends BaseModel {

	/** 数据源ID */
	private String datasourceId;

	/** 数据源名称 */
	private String datasourceName;

	/** 对象名称 */
	private String objectName;

	/** 对象类型 */
	private String objectType;

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
	 * Getting method of datasourceName
	 *
	 * @return datasourceName
	 */
	public String getDatasourceName() {
		return datasourceName;
	}

	/**
	 * Setting method of datasourceName
	 *
	 * @param datasourceName
	 */
	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	/**
	 * Getting method of objectName
	 *
	 * @return objectName
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * Setting method of objectName
	 *
	 * @param objectName
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * Getting method of objectType
	 *
	 * @return objectType
	 */
	public String getObjectType() {
		return objectType;
	}

	/**
	 * Setting method of objectType
	 *
	 * @param objectType
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

}
