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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import we.base.base.BaseService;
import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.DataUtil;
import we.base.util.VersionHolder;
import we.core.CoreProperties;
import we.core.dao.DataSourceDataDao;
import we.core.dto.ProcessDto;
import we.core.model.DataSourceDataModel;
import we.core.service.PageDataUpdateService;
import we.website.model.DataSourceColumnModel;
import we.website.model.PageDataSourceColumnModel;
import we.website.model.PageDataSourceModel;

/**
 * 画面数据更新Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class PageDataUpdateServiceImpl extends BaseService implements PageDataUpdateService {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private DataSourceDataDao dataSourceDataDao;

	@Autowired
	private VersionHolder versionHolder;

	/**
	 * 数据插入
	 */
	public int insertData(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> insertData, ProcessDto proceeDto, List<Object> resultList) throws Exception {

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

		/** 插入列信息 */
		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			if (StringUtils.hasText(columnInfo.getLinkDatasourceIndex())) {
				continue;
			}

			// 默认值
			if (StringUtils.hasText(CommonUtil.toString(insertData.get(columnInfo.getColumnId()))) == false) {
				if (StringUtils.hasText(columnInfo.getDefaultValue())) {
					insertData.put(columnInfo.getColumnId(), DataUtil.convertDefaultValue(columnInfo.getDefaultValue(), insertData));
				} else if (CommonUtil.equals(columnInfo.getNotnull(), "1")) {
					logger.error("请输入" + columnInfo.getDisplayName());
					throw new FacadeException("s.common.error.param");
				} else {
					continue;
				}
			}

			// 保存插入列
			columnList1.add(columnInfo);

			// 保存插入值
			String strValue = CommonUtil.toString(insertData.get(columnInfo.getColumnId()));
			strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
			dataParam.put(columnInfo.getColumnId(), strValue);
		}

		// 登录数据源数据
		return dataSourceDataDao.insertDataSourceData(dataSourceDataModel);
	}

	/**
	 * 批量数据插入
	 */
	public int batchInsertData(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			List<Map<String, Object>> insertDataList, ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());

		/** 属性信息列表1 */
		List<DataSourceColumnModel> columnList1 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList1(columnList1);
		List<String> columnIdList = new ArrayList<String>();

		/** 插入数据 */
		List<Map<String, String>> dataParamList = new ArrayList<Map<String, String>>();
		dataSourceDataModel.setDataParamList(dataParamList);

		int result = 0;

		for (Map<String, Object> insertData : insertDataList) {
			Map<String, String> dataParam = new HashMap<String, String>();
			dataParamList.add(dataParam);

			/** 插入列信息 */
			for (PageDataSourceColumnModel columnInfo : columnInfoList) {
				if (StringUtils.hasText(columnInfo.getLinkDatasourceIndex())) {
					continue;
				}

				// 默认值
				if (StringUtils.hasText(CommonUtil.toString(insertData.get(columnInfo.getColumnId()))) == false) {
					if (StringUtils.hasText(columnInfo.getDefaultValue())) {
						insertData.put(columnInfo.getColumnId(), DataUtil.convertDefaultValue(columnInfo.getDefaultValue(), insertData));
					} else if (CommonUtil.equals(columnInfo.getNotnull(), "1")) {
						logger.error("请输入" + columnInfo.getDisplayName());
						throw new FacadeException("s.common.error.param");
					} else {
						continue;
					}
				}

				// 保存插入列
				if (columnIdList.contains(columnInfo.getColumnId()) == false) {
					columnList1.add(columnInfo);
					columnIdList.add(columnInfo.getColumnId());
				}

				// 保存插入值
				String strValue = CommonUtil.toString(insertData.get(columnInfo.getColumnId()));
				strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
				dataParam.put(columnInfo.getColumnId(), strValue);
			}

			// 登录数据源数据
			if (websiteProp.getBatchInsertMaxCount() > 0
					&& dataParamList.size() >= websiteProp.getBatchInsertMaxCount()) {
				result += dataSourceDataDao.batchInsertDataSourceData(dataSourceDataModel);
				dataParamList.clear();
			}
		}

		// 登录数据源数据
		if (dataParamList.size() > 0) {
			result += dataSourceDataDao.batchInsertDataSourceData(dataSourceDataModel);
		}
		return result;
	}

	/**
	 * 数据更新(主键)
	 */
	public int updateData(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> updateData, Map<String, Object> conditionData, ProcessDto proceeDto,
			List<Object> resultList) throws Exception {

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());

		// 列信息
		Map<String, PageDataSourceColumnModel> columnInfoMap = new HashMap<String, PageDataSourceColumnModel>();

		/** 属性信息列表1 */
		List<DataSourceColumnModel> columnList1 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList1(columnList1);

		/** 属性信息列表1 */
		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);
		}

		/** 插入数据 */
		Map<String, String> dataParam = new HashMap<String, String>();
		dataSourceDataModel.setDataParam(dataParam);

		if (updateData != null) {
			for (String columnId : updateData.keySet()) {
				PageDataSourceColumnModel columnInfo = columnInfoMap.get(columnId);
				if (columnInfo == null) {
					continue;
				}
				if (StringUtils.hasText(columnInfo.getLinkDatasourceIndex())) {
					continue;
				}

				if (StringUtils.hasText(CommonUtil.toString(updateData.get(columnId))) == false) {
					if (StringUtils.hasText(columnInfo.getDefaultValue())) {
						updateData.put(columnId, DataUtil.convertDefaultValue(columnInfo.getDefaultValue(), updateData));
					} else if (CommonUtil.equals(columnInfo.getNotnull(), "1")) {
						logger.error("请输入" + columnInfo.getDisplayName());
						throw new FacadeException("s.common.error.param");
					}
				}

				columnList1.add(columnInfo);

				// 保存插入值
				String strValue = CommonUtil.toString(updateData.get(columnId));
				strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
				dataParam.put(columnId, strValue);
			}
		}

		/** 属性信息列表2 */
		List<DataSourceColumnModel> columnList2 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList2(columnList2);

		/** 条件数据 */
		Map<String, Object> conditionParam = new HashMap<String, Object>();
		dataSourceDataModel.setConditionParam(conditionParam);

		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			// 主键
			if (CommonUtil.equals(columnInfo.getPrimaryKey(), "1") == false) {
				continue;
			}
			columnList2.add(columnInfo);
			String columnId = columnInfo.getColumnId();

			String strValue = CommonUtil.toString(conditionData.get(columnId));
			strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
			conditionParam.put(columnId, strValue);
		}

		// 登录数据源数据
		return dataSourceDataDao.updateDataSourceData(dataSourceDataModel);
	}

	/**
	 * 数据更新(参照键)
	 */
	public int updateDataByFK(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> updateData, Map<String, Object> conditionData, String keyIndex, ProcessDto proceeDto,
			List<Object> resultList) throws Exception {

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());

		// 列信息
		Map<String, PageDataSourceColumnModel> columnInfoMap = new HashMap<String, PageDataSourceColumnModel>();

		/** 属性信息列表1 */
		List<DataSourceColumnModel> columnList1 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList1(columnList1);

		/** 属性信息列表1 */
		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);
		}

		/** 插入数据 */
		Map<String, String> dataParam = new HashMap<String, String>();
		dataSourceDataModel.setDataParam(dataParam);

		if (updateData != null) {
			for (String columnId : updateData.keySet()) {
				PageDataSourceColumnModel columnInfo = columnInfoMap.get(columnId);
				if (columnInfo == null) {
					continue;
				}
				if (StringUtils.hasText(columnInfo.getLinkDatasourceIndex())) {
					continue;
				}
				if (StringUtils.hasText(CommonUtil.toString(updateData.get(columnId))) == false) {
					if (StringUtils.hasText(columnInfo.getDefaultValue())) {
						updateData.put(columnId, DataUtil.convertDefaultValue(columnInfo.getDefaultValue(), updateData));
					} else if (CommonUtil.equals(columnInfo.getNotnull(), "1")) {
						logger.error("请输入" + columnInfo.getDisplayName());
						throw new FacadeException("s.common.error.param");
					}
				}

				columnList1.add(columnInfo);

				// 保存插入值
				String strValue = CommonUtil.toString(updateData.get(columnId));
				strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
				dataParam.put(columnId, strValue);
			}
		}

		/** 属性信息列表2 */
		List<DataSourceColumnModel> columnList2 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList2(columnList2);

		/** 条件数据 */
		Map<String, Object> conditionParam = new HashMap<String, Object>();
		dataSourceDataModel.setConditionParam(conditionParam);

		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			// 参照键
			if (CommonUtil.equals(columnInfo.getReferKey(keyIndex), "1") == false) {
				continue;
			}
			columnList2.add(columnInfo);
			String columnId = columnInfo.getColumnId();

			String strValue = CommonUtil.toString(conditionData.get(columnId));
			strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
			conditionParam.put(columnId, strValue);
		}

		// 登录数据源数据
		return dataSourceDataDao.updateDataSourceData(dataSourceDataModel);
	}

	/**
	 * 数据删除(主键)
	 */
	public int deleteDataByKey(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> conditionData, ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());

		// 列信息
		Map<String, PageDataSourceColumnModel> columnInfoMap = new HashMap<String, PageDataSourceColumnModel>();

		/** 属性信息列表1 */
		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);
		}

		/** 属性信息列表2 */
		List<DataSourceColumnModel> columnList2 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList2(columnList2);

		/** 条件数据 */
		Map<String, Object> conditionParam = new HashMap<String, Object>();
		dataSourceDataModel.setConditionParam(conditionParam);

		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			// 主键
			if (CommonUtil.equals(columnInfo.getPrimaryKey(), "1") == false) {
				continue;
			}
			columnList2.add(columnInfo);
			String columnId = columnInfo.getColumnId();

			String strValue = CommonUtil.toString(conditionData.get(columnId));
			strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
			conditionParam.put(columnId, strValue);
		}

		// 登录数据源数据
		return dataSourceDataDao.deleteDataSourceData(dataSourceDataModel);
	}

	/**
	 * 数据删除(参照键)
	 */
	public int deleteDataByFKey(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> conditionData, String keyIndex,
			ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());

		// 列信息
		Map<String, PageDataSourceColumnModel> columnInfoMap = new HashMap<String, PageDataSourceColumnModel>();

		/** 属性信息列表1 */
		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);
		}

		/** 属性信息列表2 */
		List<DataSourceColumnModel> columnList2 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList2(columnList2);

		/** 条件数据 */
		Map<String, Object> conditionParam = new HashMap<String, Object>();
		dataSourceDataModel.setConditionParam(conditionParam);

		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			// 外键
			if (CommonUtil.equals(columnInfo.getReferKey(keyIndex), "1") == false) {
				continue;
			}
			columnList2.add(columnInfo);
			String columnId = columnInfo.getColumnId();

			String strValue = CommonUtil.toString(conditionData.get(columnId));
			strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
			conditionParam.put(columnId, strValue);
		}

		// 登录数据源数据
		return dataSourceDataDao.deleteDataSourceData(dataSourceDataModel);
	}
}
