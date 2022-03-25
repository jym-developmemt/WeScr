/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service;

import java.util.List;
import java.util.Map;

import we.base.dto.PaginationData;
import we.core.dto.ProcessDto;
import we.website.model.PageDataSourceColumnModel;
import we.website.model.PageDataSourceModel;

/**
 * 画面数据查询Service
 *
 * @author cp0
 * @since 3.0
 */
public interface PageDataSearchService {

	/**
	 * 单条数据检索
	 */
	public Map<String, Object> findData(PageDataSourceModel dataSourceInfo,
			List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> searchCondition, Map<String, Object> addonMap,
			ProcessDto proceeDto, List<Object> resultList) throws Exception;

	/**
	 * 全数据检索
	 */
	public List<Map<String, Object>> searchDataList(PageDataSourceModel dataSourceInfo,
			List<PageDataSourceColumnModel> columnInfoList, Map<String, Object> searchCondition,
			Map<String, Object> addonMap,
			ProcessDto proceeDto, List<Object> resultList) throws Exception;

	/**
	 * 数据分页检索
	 */
	public PaginationData pagingDataList(PageDataSourceModel dataSourceInfo,
			List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> pagingParam, Map<String, Object> searchCondition, Map<String, Object> addonMap,
			ProcessDto proceeDto, List<Object> resultList) throws Exception;

	/**
	 * 数据件数检索
	 */
	public int coundData(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> searchCondition, Map<String, Object> addonMap,
			ProcessDto proceeDto, List<Object> resultList) throws Exception;

	/**
	 * 数据树检索
	 */
	public Object searchDataTree(PageDataSourceModel dataSourceInfo,
			List<PageDataSourceColumnModel> columnInfoList, Map<String, Object> searchCondition,
			Map<String, Object> addonMap,
			ProcessDto proceeDto, List<Object> resultList) throws Exception;
}
