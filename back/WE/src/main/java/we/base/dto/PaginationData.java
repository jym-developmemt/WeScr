/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.dto;

import java.util.List;
import java.util.Map;

import we.base.base.PaginationModel;

/**
 * 分页数据
 *
 * @author cp0
 * @since 0.0
 */
public class PaginationData {

	/** 分页模型 */
	private PaginationModel pageInfo;

	/** 分页数据 */
	private List<Map<String, Object>> dataList;

	/**
	 * Getting method of pageInfo
	 *
	 * @return pageInfo
	 */
	public PaginationModel getPageInfo() {
		return pageInfo;
	}

	/**
	 * Setting method of pageInfo
	 *
	 * @param pageInfo
	 */
	public void setPageInfo(PaginationModel pageInfo) {
		this.pageInfo = pageInfo;
	}

	/**
	 * Getting method of dataList
	 *
	 * @return dataList
	 */
	public List<Map<String, Object>> getDataList() {
		return dataList;
	}

	/**
	 * Setting method of dataList
	 *
	 * @param dataList
	 */
	public void setDataList(List<Map<String, Object>> dataList) {
		this.dataList = dataList;
	}
}
