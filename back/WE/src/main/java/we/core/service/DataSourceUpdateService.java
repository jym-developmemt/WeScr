/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service;

import java.util.List;
import java.util.Map;

import we.website.model.DataSourceColumnModel;
import we.website.model.DataSourceInfoModel;

/**
 * 数据源数据更新Service
 *
 * @author cp0
 * @since 0.0
 */
public interface DataSourceUpdateService {

	/**
	 * 数据插入
	 */
	public int insertData(DataSourceInfoModel dataSourceInfo, List<DataSourceColumnModel> columnInfoList,
			Map<String, Object> insertData) throws Exception;

	/**
	 * 数据更新
	 */
	public int updateData(DataSourceInfoModel dataSourceInfo, List<DataSourceColumnModel> columnInfoList,
			Map<String, Object> updateData, Map<String, Object> conditionData) throws Exception;

	/**
	 * 数据删除
	 */
	public int deleteData(DataSourceInfoModel dataSourceInfo, List<DataSourceColumnModel> columnInfoList,
			Map<String, Object> conditionData) throws Exception;
}
