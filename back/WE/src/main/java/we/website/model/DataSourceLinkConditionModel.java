/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import org.springframework.util.StringUtils;

import we.base.base.BaseModel;
import we.base.util.CommonUtil;

/**
 * 关联数据条件信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class DataSourceLinkConditionModel extends BaseModel {

	/** 列ID1 */
	private String columnId1;

	/** 列ID2 */
	private String columnId2;

	/**
	 * Getting method of columnId1
	 *
	 * @return columnId1
	 */
	public String getColumnId1() {
		return columnId1;
	}

	/**
	 * Setting method of columnId1
	 *
	 * @param columnId1
	 */
	public void setColumnId1(String columnId1) {
		this.columnId1 = columnId1;
	}

	/**
	 * Getting method of columnId2
	 *
	 * @return columnId2
	 */
	public String getColumnId2() {
		return columnId2;
	}

	/**
	 * Setting method of columnId2
	 *
	 * @param columnId2
	 */
	public void setColumnId2(String columnId2) {
		this.columnId2 = columnId2;
	}

	/**
	 * Getting method of linkType
	 *
	 * @return linkType
	 */
	public int getLinkType() {
		if (CommonUtil.equals(columnId2, "null") || StringUtils.isEmpty(columnId2)) {
			return 9;
		}
		if (columnId2.startsWith("'") && columnId2.endsWith("'")) {
			return 1;
		}
		return 0;
	}
}
