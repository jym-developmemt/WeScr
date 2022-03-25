/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service;

import java.util.List;
import java.util.Map;

import we.core.dto.ProcessDto;
import we.website.model.PageDataSourceColumnModel;
import we.website.model.PageDataSourceModel;

/**
 * 画面数据更新Service
 *
 * @author cp0
 * @since 0.0
 */
public interface PageDataUpdateService {

	/**
	 * 数据插入
	 */
	public int insertData(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> insertData,
			ProcessDto proceeDto, List<Object> resultList) throws Exception;

	/**
	 * 批量数据插入
	 */
	public int batchInsertData(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			List<Map<String, Object>> insertDataList,
			ProcessDto proceeDto, List<Object> resultList) throws Exception;

	/**
	 * 数据更新(主键)
	 */
	public int updateData(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> updateData, Map<String, Object> conditionData,
			ProcessDto proceeDto, List<Object> resultList) throws Exception;

	/**
	 * 数据更新(参照键)
	 */
	public int updateDataByFK(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> updateData, Map<String, Object> conditionData, String keyIndex,
			ProcessDto proceeDto, List<Object> resultList) throws Exception;

	/**
	 * 数据删除(主键)
	 */
	public int deleteDataByKey(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> conditionData,
			ProcessDto proceeDto, List<Object> resultList) throws Exception;

	/**
	 * 数据删除(参照键)
	 */
	public int deleteDataByFKey(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> conditionData, String keyIndex,
			ProcessDto proceeDto, List<Object> resultList) throws Exception;
}
