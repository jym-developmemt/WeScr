/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import we.base.base.BaseService;
import we.base.util.CommonUtil;
import we.core.dao.DataSourceDataDao;
import we.core.model.DataSourceDataModel;
import we.core.service.DataSourceUpdateService;
import we.website.model.DataSourceColumnModel;
import we.website.model.DataSourceInfoModel;

/**
 * 数据源数据更新Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class DataSourceUpdateServiceImpl extends BaseService implements DataSourceUpdateService {

	@Autowired
	private DataSourceDataDao dataSourceDataDao;

	/**
	 * 数据插入
	 */
	public int insertData(DataSourceInfoModel dataSourceInfo, List<DataSourceColumnModel> columnInfoList,
			Map<String, Object> insertData) throws Exception {

		// 列信息
		Map<String, DataSourceColumnModel> columnInfoMap = new HashMap<String, DataSourceColumnModel>();
		for (DataSourceColumnModel columnInfo : columnInfoList) {
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);
		}

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());

		/** 属性信息列表1 */
		List<DataSourceColumnModel> columnList1 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList1(columnList1);

		/** 插入数据 */
		Map<String, String> dataParam = new HashMap<String, String>();
		dataSourceDataModel.setDataParam(dataParam);

		for (String columnId : insertData.keySet()) {
			DataSourceColumnModel columnInfo = columnInfoMap.get(columnId);
			if (columnInfo == null) {
				continue;
			}

			// 保存插入列
			columnList1.add(columnInfo);

			// 保存插入值
			String strValue = CommonUtil.toString(insertData.get(columnInfo.getColumnId()));
			if (StringUtils.isEmpty(strValue)) {
				strValue = null;
			}
			dataParam.put(columnInfo.getColumnId(), strValue);
		}

		// 登录数据源数据
		return dataSourceDataDao.insertDataSourceData(dataSourceDataModel);
	}

	/**
	 * 数据更新
	 */
	public int updateData(DataSourceInfoModel dataSourceInfo, List<DataSourceColumnModel> columnInfoList,
			Map<String, Object> updateData, Map<String, Object> conditionData) throws Exception {

		// 列信息
		Map<String, DataSourceColumnModel> columnInfoMap = new HashMap<String, DataSourceColumnModel>();
		for (DataSourceColumnModel columnInfo : columnInfoList) {
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);
		}

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());

		/** 属性信息列表1 */
		List<DataSourceColumnModel> columnList1 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList1(columnList1);

		/** 插入数据 */
		Map<String, String> dataParam = new HashMap<String, String>();
		dataSourceDataModel.setDataParam(dataParam);

		for (String columnId : updateData.keySet()) {
			DataSourceColumnModel columnInfo = columnInfoMap.get(columnId);
			if (columnInfo == null) {
				continue;
			}

			// 保存更新列
			columnList1.add(columnInfo);

			// 保存更新值
			String strValue = CommonUtil.toString(updateData.get(columnId));
			if (StringUtils.isEmpty(strValue)) {
				strValue = null;
			}
			dataParam.put(columnId, strValue);
		}

		/** 属性信息列表2 */
		List<DataSourceColumnModel> columnList2 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList2(columnList2);

		/** 条件数据 */
		Map<String, Object> conditionParam = new HashMap<String, Object>();
		dataSourceDataModel.setConditionParam(conditionParam);

		for (String columnId : conditionData.keySet()) {
			DataSourceColumnModel columnInfo = columnInfoMap.get(columnId);
			if (columnInfo == null) {
				continue;
			}

			columnList2.add(columnInfo);

			String strValue = CommonUtil.toString(conditionData.get(columnId));
			if (StringUtils.isEmpty(strValue)) {
				strValue = null;
			}
			conditionParam.put(columnId, strValue);
		}

		// 登录数据源数据
		return dataSourceDataDao.updateDataSourceData(dataSourceDataModel);
	}

	/**
	 * 数据删除
	 */
	public int deleteData(DataSourceInfoModel dataSourceInfo, List<DataSourceColumnModel> columnInfoList,
			Map<String, Object> conditionData) throws Exception {

		// 列信息
		Map<String, DataSourceColumnModel> columnInfoMap = new HashMap<String, DataSourceColumnModel>();
		for (DataSourceColumnModel columnInfo : columnInfoList) {
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);
		}

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());

		/** 属性信息列表2 */
		List<DataSourceColumnModel> columnList2 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList2(columnList2);

		/** 条件数据 */
		Map<String, Object> conditionParam = new HashMap<String, Object>();
		dataSourceDataModel.setConditionParam(conditionParam);

		for (String columnId : conditionData.keySet()) {
			DataSourceColumnModel columnInfo = columnInfoMap.get(columnId);
			if (columnInfo == null) {
				continue;
			}

			columnList2.add(columnInfo);

			String strValue = CommonUtil.toString(conditionData.get(columnId));
			if (StringUtils.isEmpty(strValue)) {
				strValue = null;
			}
			conditionParam.put(columnId, strValue);
		}

		// 登录数据源数据
		return dataSourceDataDao.deleteDataSourceData(dataSourceDataModel);
	}
}
