/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import we.base.base.BaseService;
import we.base.base.PaginationModel;
import we.base.dto.PaginationData;
import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.DataUtil;
import we.base.util.EncryptorUtil;
import we.base.util.VersionHolder;
import we.core.CoreProperties;
import we.core.dao.DataSourceDataDao;
import we.core.dto.ProcessDto;
import we.core.model.DataSourceDataModel;
import we.core.model.FunctionDepartmentModel;
import we.core.model.UserGrantedAuthorityModel;
import we.core.service.PageDataSearchService;
import we.core.service.UserGrantedAuthorityService;
import we.website.dao.PageDataSourceColumnDao;
import we.website.dao.PageDataSourceDao;
import we.website.model.DataSourceColumnModel;
import we.website.model.DataSourceLinkConditionModel;
import we.website.model.DataSourceLinkTableModel;
import we.website.model.PageDataSourceColumnModel;
import we.website.model.PageDataSourceModel;
import we.website.model.UserInfoModel;

/**
 * 画面数据查询Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class PageDataSearchServiceImpl extends BaseService implements PageDataSearchService {

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private DataSourceDataDao dataSourceDataDao;

	@Autowired
	private PageDataSourceDao pageDataSourceDao;

	@Autowired
	private PageDataSourceColumnDao pageDataSourceColumnDao;

	@Autowired
	private VersionHolder versionHolder;

	@Autowired
	@Qualifier("functionDepartmentService")
	private UserGrantedAuthorityService functionDepartmentService;

	/**
	 * 单条数据检索
	 */
	public Map<String, Object> findData(PageDataSourceModel dataSourceInfo,
			List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> searchCondition, Map<String, Object> addonMap,
			ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());
		dataSourceDataModel.setDatasourceIndex(dataSourceInfo.getDatasourceIndex());

		// 列信息
		Map<String, PageDataSourceColumnModel> columnInfoMap = new HashMap<String, PageDataSourceColumnModel>();

		/** 属性信息列表1 */
		List<DataSourceColumnModel> columnList1 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList1(columnList1);
		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);
			if (StringUtils.hasText(columnInfo.getLinkDatasourceIndex())) {
				continue;
			}
			columnList1.add(columnInfo);
		}

		/** 列信息列表2 */
		List<DataSourceColumnModel> columnList2 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList2(columnList2);
		/** 关联输入条件 */
		Map<String, List<PageDataSourceColumnModel>> linkColumnInputConditionMap = new HashMap<String, List<PageDataSourceColumnModel>>();
		dataSourceDataModel.setLinkColumnInputConditionMap(linkColumnInputConditionMap);
		/** 查询条件 */
		Map<String, Object> conditionParam = new HashMap<String, Object>();
		dataSourceDataModel.setConditionParam(conditionParam);

		if (searchCondition != null) {
			generateConditionParam(dataSourceInfo, columnInfoList, searchCondition, addonMap, proceeDto, resultList,
					columnInfoMap, dataSourceDataModel, columnList2, linkColumnInputConditionMap, conditionParam);
		}

		if (addonMap != null && CommonUtil.equals(CommonUtil.toString(addonMap.get("condConcatType")), "1")) {
			dataSourceDataModel.setCondConcatType("OR");
		}

		// 单条检索实行
		Map<String, Object> resultData = dataSourceDataDao.findDataSourceData(dataSourceDataModel);

		// 关联数据源值取得
		convertLinkData(columnInfoList, resultData, proceeDto, resultList, addonMap);

		return convertDataResult(columnList1, resultData);
	}

	/**
	 * 全数据检索
	 */
	public List<Map<String, Object>> searchDataList(PageDataSourceModel dataSourceInfo,
			List<PageDataSourceColumnModel> columnInfoList, Map<String, Object> searchCondition,
			Map<String, Object> addonMap,
			ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());
		dataSourceDataModel.setDatasourceIndex(dataSourceInfo.getDatasourceIndex());

		// 列信息
		Map<String, PageDataSourceColumnModel> columnInfoMap = new HashMap<String, PageDataSourceColumnModel>();

		/** 属性信息列表1 */
		List<DataSourceColumnModel> columnList1 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList1(columnList1);
		/** 列信息列表3 */
		List<PageDataSourceColumnModel> columnList3 = new ArrayList<PageDataSourceColumnModel>();
		dataSourceDataModel.setColumnList3(columnList3);

		String sortColumn = null;
		String sortOrder = null;
		PageDataSourceColumnModel sortColumnInfo = null;
		if (addonMap != null) {
			sortColumn = CommonUtil.toString(addonMap.get("sortColumn"));
			sortOrder = CommonUtil.toString(addonMap.get("sortOrder"));
		}

		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);

			if (StringUtils.hasText(columnInfo.getLinkDatasourceIndex())) {
				continue;
			}

			if (CommonUtil.equals(columnInfo.getColumnId(), sortColumn)) {
				sortColumnInfo = columnInfo;
			}

			columnList1.add(columnInfo);

			if (StringUtils.hasText(columnInfo.getOrderIndex())) {
				columnList3.add(columnInfo);
			}
		}

		// 排序项目排序
		columnList3.sort(new Comparator<PageDataSourceColumnModel>() {
			@Override
			public int compare(PageDataSourceColumnModel o1, PageDataSourceColumnModel o2) {
				int orderIndex1 = Integer.valueOf(o1.getOrderIndex());
				int orderIndex2 = Integer.valueOf(o2.getOrderIndex());
				return Integer.compare(orderIndex1, orderIndex2);
			}
		});

		if (sortColumnInfo != null) {
			PageDataSourceColumnModel customColumnInfo = new PageDataSourceColumnModel();
			customColumnInfo.setColumnId(dataSourceInfo.getDatasourceIndex());
			customColumnInfo.setColumnId(sortColumnInfo.getColumnId());
			if (StringUtils.hasText(sortOrder)) {
				customColumnInfo.setOrderMethod(sortOrder);
			}
			columnList3.add(0, customColumnInfo);
		}

		/** 列信息列表2 */
		List<DataSourceColumnModel> columnList2 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList2(columnList2);
		/** 关联输入条件 */
		Map<String, List<PageDataSourceColumnModel>> linkColumnInputConditionMap = new HashMap<String, List<PageDataSourceColumnModel>>();
		dataSourceDataModel.setLinkColumnInputConditionMap(linkColumnInputConditionMap);
		/** 查询条件 */
		Map<String, Object> conditionParam = new HashMap<String, Object>();
		dataSourceDataModel.setConditionParam(conditionParam);

		if (searchCondition != null) {
			generateConditionParam(dataSourceInfo, columnInfoList, searchCondition, addonMap, proceeDto, resultList,
					columnInfoMap, dataSourceDataModel, columnList2, linkColumnInputConditionMap, conditionParam);
		}

		if (addonMap != null && CommonUtil.equals(CommonUtil.toString(addonMap.get("condConcatType")), "1")) {
			dataSourceDataModel.setCondConcatType("OR");
		}

		// 全检索实行
		List<Map<String, Object>> resultDataList = dataSourceDataDao.searchDataSourceData(dataSourceDataModel);

		// 关联数据源值取得
		for (Map<String, Object> resultData : resultDataList) {
			convertLinkData(columnInfoList, resultData, proceeDto, resultList, addonMap);
		}

		return convertDataResult(columnList1, resultDataList);
	}

	/**
	 * 数据分页检索
	 */
	public PaginationData pagingDataList(PageDataSourceModel dataSourceInfo,
			List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> pagingParam, Map<String, Object> searchCondition, Map<String, Object> addonMap,
			ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());
		dataSourceDataModel.setDatasourceIndex(dataSourceInfo.getDatasourceIndex());

		// 当前页码
		dataSourceDataModel.setPageNo(1);
		// 每页显示数
		dataSourceDataModel.setPageSize(Integer.valueOf(dataSourceInfo.getPageSize()));

		// 分页信息设定
		if (pagingParam != null) {
			if (pagingParam.containsKey("pageNo")) {
				int pageNo = Integer.valueOf(pagingParam.get("pageNo").toString());
				dataSourceDataModel.setPageNo(pageNo);
			}

			if (pagingParam.containsKey("pageSize")) {
				int pageSize = Integer.valueOf(pagingParam.get("pageSize").toString());
				dataSourceDataModel.setPageSize(pageSize);
			}
		}

		// 列信息
		Map<String, PageDataSourceColumnModel> columnInfoMap = new HashMap<String, PageDataSourceColumnModel>();

		/** 属性信息列表1 */
		List<DataSourceColumnModel> columnList1 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList1(columnList1);
		/** 列信息列表3 */
		List<PageDataSourceColumnModel> columnList3 = new ArrayList<PageDataSourceColumnModel>();
		dataSourceDataModel.setColumnList3(columnList3);

		String sortColumn = null;
		String sortOrder = null;
		PageDataSourceColumnModel sortColumnInfo = null;
		if (addonMap != null) {
			sortColumn = CommonUtil.toString(addonMap.get("sortColumn"));
			sortOrder = CommonUtil.toString(addonMap.get("sortOrder"));
		}

		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);

			if (StringUtils.hasText(columnInfo.getLinkDatasourceIndex())) {
				continue;
			}

			if (CommonUtil.equals(columnInfo.getColumnId(), sortColumn)) {
				sortColumnInfo = columnInfo;
			}

			columnList1.add(columnInfo);

			if (StringUtils.hasText(columnInfo.getOrderIndex())) {
				columnList3.add(columnInfo);
			}
		}

		// 排序项目排序
		columnList3.sort(new Comparator<PageDataSourceColumnModel>() {
			@Override
			public int compare(PageDataSourceColumnModel o1, PageDataSourceColumnModel o2) {
				int orderIndex1 = Integer.valueOf(o1.getOrderIndex());
				int orderIndex2 = Integer.valueOf(o2.getOrderIndex());
				return Integer.compare(orderIndex1, orderIndex2);
			}
		});

		if (sortColumnInfo != null) {
			PageDataSourceColumnModel customColumnInfo = new PageDataSourceColumnModel();
			customColumnInfo.setColumnId(dataSourceInfo.getDatasourceIndex());
			customColumnInfo.setColumnId(sortColumnInfo.getColumnId());
			if (StringUtils.hasText(sortOrder)) {
				customColumnInfo.setOrderMethod(sortOrder);
			}
			columnList3.add(0, customColumnInfo);
		}

		/** 列信息列表2 */
		List<DataSourceColumnModel> columnList2 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList2(columnList2);
		/** 关联输入条件 */
		Map<String, List<PageDataSourceColumnModel>> linkColumnInputConditionMap = new HashMap<String, List<PageDataSourceColumnModel>>();
		dataSourceDataModel.setLinkColumnInputConditionMap(linkColumnInputConditionMap);
		/** 查询条件 */
		Map<String, Object> conditionParam = new HashMap<String, Object>();
		dataSourceDataModel.setConditionParam(conditionParam);

		if (searchCondition != null) {
			generateConditionParam(dataSourceInfo, columnInfoList, searchCondition, addonMap, proceeDto, resultList,
					columnInfoMap, dataSourceDataModel, columnList2, linkColumnInputConditionMap, conditionParam);
		}

		if (addonMap != null && CommonUtil.equals(CommonUtil.toString(addonMap.get("condConcatType")), "1")) {
			dataSourceDataModel.setCondConcatType("OR");
		}

		// 分页检索实行
		int itemCount = dataSourceDataDao.countDataSourceData(dataSourceDataModel);
		dataSourceDataModel.setItemCount(itemCount);
		if (dataSourceDataModel.getPageNo() > dataSourceDataModel.getPageTotalCount()) {
			dataSourceDataModel.setPageNo(1);
		}
		List<Map<String, Object>> dataList = dataSourceDataDao.pagingDataSourceData(dataSourceDataModel);

		// 关联数据源值取得
		for (Map<String, Object> resultData : dataList) {
			convertLinkData(columnInfoList, resultData, proceeDto, resultList, addonMap);
		}

		convertDataResult(columnList1, dataList);

		// 分页信息
		PaginationModel rtnPageInfo = new PaginationModel();
		rtnPageInfo.setItemCount(dataSourceDataModel.getItemCount());
		rtnPageInfo.setPageNo(dataSourceDataModel.getPageNo());
		rtnPageInfo.setPageSize(dataSourceDataModel.getPageSize());

		// 返回结果
		PaginationData paginationData = new PaginationData();
		paginationData.setPageInfo(rtnPageInfo);
		paginationData.setDataList(dataList);
		return paginationData;
	}

	/**
	 * 数据件数检索
	 */
	public int coundData(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> searchCondition, Map<String, Object> addonMap,
			ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());
		dataSourceDataModel.setDatasourceIndex(dataSourceInfo.getDatasourceIndex());

		// 列信息
		Map<String, PageDataSourceColumnModel> columnInfoMap = new HashMap<String, PageDataSourceColumnModel>();

		/** 属性信息列表1 */
		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);
		}

		/** 列信息列表2 */
		List<DataSourceColumnModel> columnList2 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList2(columnList2);
		/** 关联输入条件 */
		Map<String, List<PageDataSourceColumnModel>> linkColumnInputConditionMap = new HashMap<String, List<PageDataSourceColumnModel>>();
		dataSourceDataModel.setLinkColumnInputConditionMap(linkColumnInputConditionMap);
		/** 查询条件 */
		Map<String, Object> conditionParam = new HashMap<String, Object>();
		dataSourceDataModel.setConditionParam(conditionParam);

		if (searchCondition != null) {
			generateConditionParam(dataSourceInfo, columnInfoList, searchCondition, addonMap, proceeDto, resultList,
					columnInfoMap, dataSourceDataModel, columnList2, linkColumnInputConditionMap, conditionParam);
		}

		if (addonMap != null && CommonUtil.equals(CommonUtil.toString(addonMap.get("condConcatType")), "1")) {
			dataSourceDataModel.setCondConcatType("OR");
		}

		// 单条检索实行
		return dataSourceDataDao.countDataSourceData(dataSourceDataModel);
	}

	/**
	 * 数据树检索
	 */
	public Object searchDataTree(PageDataSourceModel dataSourceInfo,
			List<PageDataSourceColumnModel> columnInfoList, Map<String, Object> searchCondition,
			Map<String, Object> addonMap,
			ProcessDto proceeDto, List<Object> resultList) throws Exception {

		String currentColumnId = CommonUtil.toString(addonMap.get("currentColumnId"));
		String parentColumnId = CommonUtil.toString(addonMap.get("parentColumnId"));
		String extractFlgId = CommonUtil.toString(addonMap.get("extractFlgColumn"));
		String listResult = CommonUtil.toString(addonMap.get("listResult"));
		String fromRootNode = CommonUtil.toString(addonMap.get("fromRootNode"));

		// 检索模型
		DataSourceDataModel dataSourceDataModel = new DataSourceDataModel();
		// 检索对象
		dataSourceDataModel.setTableName(dataSourceInfo.getObjectName());
		dataSourceDataModel.setDatasourceIndex(dataSourceInfo.getDatasourceIndex());

		// 列信息
		Map<String, PageDataSourceColumnModel> columnInfoMap = new HashMap<String, PageDataSourceColumnModel>();

		/** 属性信息列表1 */
		List<DataSourceColumnModel> columnList1 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList1(columnList1);
		/** 列信息列表3 */
		List<PageDataSourceColumnModel> columnList3 = new ArrayList<PageDataSourceColumnModel>();
		dataSourceDataModel.setColumnList3(columnList3);

		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			if (StringUtils.hasText(columnInfo.getLinkDatasourceIndex())) {
				continue;
			}

			columnList1.add(columnInfo);
			columnInfoMap.put(columnInfo.getColumnId(), columnInfo);

			if (StringUtils.hasText(columnInfo.getOrderIndex())) {
				columnList3.add(columnInfo);
			}
		}

		// 排序项目排序
		columnList3.sort(new Comparator<PageDataSourceColumnModel>() {
			@Override
			public int compare(PageDataSourceColumnModel o1, PageDataSourceColumnModel o2) {
				int orderIndex1 = Integer.valueOf(o1.getOrderIndex());
				int orderIndex2 = Integer.valueOf(o2.getOrderIndex());
				return Integer.compare(orderIndex1, orderIndex2);
			}
		});

		/** 列信息列表2 */
		List<DataSourceColumnModel> columnList2 = new ArrayList<DataSourceColumnModel>();
		dataSourceDataModel.setColumnList2(columnList2);

		/** 根结点条件 */
		PageDataSourceColumnModel parentColumn = new PageDataSourceColumnModel();
		parentColumn.setDatasourceIndex(dataSourceInfo.getDatasourceIndex());
		parentColumn.setColumnId(parentColumnId);
		if (CommonUtil.equals(fromRootNode, "0") == false) {
			parentColumn.setConditionType("11");
			columnList2.add(parentColumn);
		}

		/** 查询条件 */
		Map<String, Object> conditionParam = new HashMap<String, Object>();
		dataSourceDataModel.setConditionParam(conditionParam);

		if (searchCondition != null) {
			for (String columnId : searchCondition.keySet()) {
				PageDataSourceColumnModel conditionInfo = columnInfoMap.get(columnId);
				if (conditionInfo == null) {
					continue;
				}
				if (StringUtils.hasText(CommonUtil.toString(searchCondition.get(columnId))) == false) {
					continue;
				}

				// 检索方式再设定
				if (addonMap != null) {
					String conditionType = null;

					// 自定义列检索类型
					if (addonMap.containsKey(columnId)) {
						String info = CommonUtil.toString(addonMap.get(columnId));
						if (info.length() >= 3) {
							conditionType = info.substring(1, 3).replaceAll("_", "");
						}
					}

					// 全局检索类型
					if ("1".equals(addonMap.get("alwaysEquals"))) {
						conditionType = "0";
					}

					// 检索方式再设定
					if (StringUtils.hasText(conditionType)) {
						conditionInfo.setConditionType(conditionType);
					}
				}

				columnList2.add(conditionInfo);

				String strValue = CommonUtil.toString(searchCondition.get(columnId));
				strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
				conditionParam.put(conditionInfo.getConditionColumnId(), strValue);
			}
		}

		if (addonMap != null && CommonUtil.equals(CommonUtil.toString(addonMap.get("condConcatType")), "1")) {
			dataSourceDataModel.setCondConcatType("OR");
		}

		// 全检索实行
		List<Map<String, Object>> resultDataList = subDataList(columnInfoList, dataSourceDataModel, currentColumnId,
				parentColumn, conditionParam,
				extractFlgId, proceeDto, resultList, addonMap);
		if (resultDataList.size() > 0) {
			if (CommonUtil.equals(listResult, "1")) {
				return resultDataList;
			} else {
				return resultDataList.get(0);
			}
		} else {
			return null;
		}
	}

	private List<Map<String, Object>> subDataList(List<PageDataSourceColumnModel> columnInfoList,
			DataSourceDataModel dataSourceDataModel,
			String currentColumnId, PageDataSourceColumnModel parentColumn, Map<String, Object> conditionParam,
			String extractFlgId,
			ProcessDto proceeDto, List<Object> resultList, Map<String, Object> addonMap) throws Exception {
		// 返回数据列表
		List<Map<String, Object>> rtnDataList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> resultDataList = dataSourceDataDao.searchDataSourceData(dataSourceDataModel);
		for (Map<String, Object> resultData : resultDataList) {

			// 忽略节点
			if (StringUtils.hasText(extractFlgId) && "2".equals(resultData.get(extractFlgId))) {
				continue;
			}

			// 关联数据源值取得
			convertLinkData(columnInfoList, resultData, proceeDto, resultList, addonMap);

			parentColumn.setConditionType("0");

			String strValue = CommonUtil.toString(resultData.get(currentColumnId));
			strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
			conditionParam.put(parentColumn.getConditionColumnId(), strValue);

			if (dataSourceDataModel.getColumnList2().contains(parentColumn) == false) {
				dataSourceDataModel.getColumnList2().add(parentColumn);
			}

			List<Map<String, Object>> subResultDataList = subDataList(columnInfoList, dataSourceDataModel,
					currentColumnId, parentColumn,
					conditionParam, extractFlgId, proceeDto, resultList, addonMap);

			// 提取子节点
			if (StringUtils.hasText(extractFlgId) && "1".equals(resultData.get(extractFlgId))) {
				// 父节点ID设定
				for (Map<String, Object> subResultData : subResultDataList) {
					subResultData.put(parentColumn.getColumnId(), resultData.get(parentColumn.getColumnId()));
					rtnDataList.add(subResultData);
				}
				continue;
			}

			if (subResultDataList.size() > 0) {
				resultData.put("_children_", subResultDataList);
			}

			rtnDataList.add(resultData);
		}

		return rtnDataList;
	}

	/**
	 * 生成检索条件
	 */
	private void generateConditionParam(PageDataSourceModel dataSourceInfo, List<PageDataSourceColumnModel> columnInfoList,
			Map<String, Object> searchCondition, Map<String, Object> addonMap, ProcessDto proceeDto, List<Object> resultList,
			Map<String, PageDataSourceColumnModel> columnInfoMap, DataSourceDataModel dataSourceDataModel, List<DataSourceColumnModel> columnList2,
			Map<String, List<PageDataSourceColumnModel>> linkColumnInputConditionMap, Map<String, Object> conditionParam) throws Exception {

		for (String columnId : searchCondition.keySet()) {
			if (StringUtils.hasText(CommonUtil.toString(searchCondition.get(columnId))) == false) {
				continue;
			}

			String strValue = CommonUtil.toString(searchCondition.get(columnId));
			strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());

			if (columnId.indexOf(".") > -1) {
				// 关联数据源条件
				convertLinkCondition(columnId, columnInfoMap, dataSourceDataModel, columnList2,
						linkColumnInputConditionMap, conditionParam,
						addonMap, strValue);
			} else {

				PageDataSourceColumnModel conditionInfo = columnInfoMap.get(columnId);
				if (conditionInfo == null) {
					continue;
				}

				// 检索方式再设定
				if (addonMap != null) {
					String conditionType = null;

					// 自定义列检索类型
					if (addonMap.containsKey(columnId)) {
						String info = CommonUtil.toString(addonMap.get(columnId));
						if (info.length() >= 3) {
							conditionType = info.substring(1, 3).replaceAll("_", "");
						}
					}

					// 全局检索类型
					if ("1".equals(addonMap.get("alwaysEquals"))) {
						conditionType = "0";
					}

					// 检索方式再设定
					if (StringUtils.hasText(conditionType)) {
						conditionInfo.setConditionType(conditionType);
					}
				}

				columnList2.add(conditionInfo);

				// 用户管理部门处理
				if (CommonUtil.equals(conditionInfo.getConditionType(), "50")) {

					List<FunctionDepartmentModel> conditionList = new ArrayList<FunctionDepartmentModel>();
					UserInfoModel param = new UserInfoModel();
					param.setVersionId(versionHolder.getLastVersion());
					param.setUserId(conditionInfo.getLoginUser());
					List<UserGrantedAuthorityModel> grantedAuthorityList = functionDepartmentService.searchUserGrantedAuthority(param);
					for (UserGrantedAuthorityModel userGrantedAuthority: grantedAuthorityList) {
						if (userGrantedAuthority instanceof FunctionDepartmentModel) {
							FunctionDepartmentModel functionDepartment = (FunctionDepartmentModel) userGrantedAuthority;
							if (CommonUtil.equals(functionDepartment.getFunctionId(), strValue)) {
								conditionList.add(functionDepartment);
							}
						}
					}

					if (conditionList.size() == 0) {
						FunctionDepartmentModel nullCondition = new FunctionDepartmentModel();
						nullCondition.setManageType("1");
						nullCondition.setDepartmentId("_null_");
						conditionList.add(nullCondition);
					}

					conditionParam.put(conditionInfo.getConditionColumnId(), conditionList);
				} else {
					conditionParam.put(conditionInfo.getConditionColumnId(), strValue);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void convertLinkCondition(String columnId, Map<String, PageDataSourceColumnModel> columnInfoMap,
			DataSourceDataModel dataSourceDataModel,
			List<DataSourceColumnModel> columnList2,
			Map<String, List<PageDataSourceColumnModel>> linkColumnInputConditionMap,
			Map<String, Object> conditionParam,
			Map<String, Object> addonMap, String strValue) {
		String[] columnInfo = columnId.split("[.]");
		PageDataSourceColumnModel conditionInfo = columnInfoMap.get(columnInfo[0]);
		if (conditionInfo == null || StringUtils.hasText(conditionInfo.getLinkDatasourceIndex()) == false) {
			return;
		}

		boolean hasTable = false;
		for (DataSourceLinkTableModel linkTable : dataSourceDataModel.getLinkTableList()) {
			if (CommonUtil.equals(linkTable.getAliasName(), columnInfo[0])) {
				hasTable = true;
			}
		}

		// 关联数据源列信息
		Map<String, PageDataSourceColumnModel> conditionColumnInfoMap = new HashMap<String, PageDataSourceColumnModel>();
		PageDataSourceColumnModel param = new PageDataSourceColumnModel();
		param.setGroupId(conditionInfo.getGroupId());
		param.setPageId(conditionInfo.getPageId());
		param.setDatasourceIndex(conditionInfo.getLinkDatasourceIndex());

		List<PageDataSourceColumnModel> linkColumnList = pageDataSourceColumnDao.searchDataSourceColumn(param);
		for (PageDataSourceColumnModel linkColumn : linkColumnList) {
			conditionColumnInfoMap.put(linkColumn.getColumnId(), linkColumn);
		}

		if (hasTable == false) {
			// 关联数据源信息
			PageDataSourceModel dataSourceInfo = new PageDataSourceModel();
			dataSourceInfo.setGroupId(conditionInfo.getGroupId());
			dataSourceInfo.setPageId(conditionInfo.getPageId());
			dataSourceInfo.setDatasourceIndex(conditionInfo.getLinkDatasourceIndex());
			dataSourceInfo = pageDataSourceDao.findPageDataSource(dataSourceInfo);

			DataSourceLinkTableModel linkTable = new DataSourceLinkTableModel();
			linkTable.setTableName(dataSourceInfo.getObjectName());
			linkTable.setAliasName(columnInfo[0]);

			if (CommonUtil.equals(dataSourceInfo.getSearchType(), "0")) {
				if (CommonUtil.equals(conditionInfo.getJoinType(), "1")) {
					linkTable.setJoinType("LEFT");
				} else if (CommonUtil.equals(conditionInfo.getJoinType(), "2")) {
					linkTable.setJoinType("RIGHT");
				}
				dataSourceDataModel.getLinkTableList().add(linkTable);
			} else {
				dataSourceDataModel.getExistTableList().add(linkTable);
			}

			try {
				ObjectMapper objectMapper = new ObjectMapper();
				HashMap<String, String> jCondition = objectMapper.readValue(conditionInfo.getLinkCondition(),
						HashMap.class);

				List<DataSourceLinkConditionModel> linkConditionList = new ArrayList<DataSourceLinkConditionModel>();
				for (Map.Entry<String, String> entry : jCondition.entrySet()) {
					DataSourceLinkConditionModel linkCondition = new DataSourceLinkConditionModel();
					linkCondition.setColumnId1(entry.getKey());
					linkCondition.setColumnId2(entry.getValue());
					linkConditionList.add(linkCondition);
				}
				dataSourceDataModel.getLinkConditionMap().put(columnInfo[0], linkConditionList);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// 检索条件转为表关联条件
		if (CommonUtil.equals(conditionInfo.getLinkConditionFlg(), "1")) {
			if (linkColumnInputConditionMap.containsKey(columnInfo[0]) == false) {
				linkColumnInputConditionMap.put(columnInfo[0], new ArrayList<PageDataSourceColumnModel>());
			}
			linkColumnInputConditionMap.get(columnInfo[0]).add(conditionColumnInfoMap.get(columnInfo[1]));
		} else {
			columnList2.add(conditionColumnInfoMap.get(columnInfo[1]));
		}

		conditionParam.put(conditionColumnInfoMap.get(columnInfo[1]).getConditionColumnId(), strValue);
	}

	private List<Map<String, Object>> convertDataResult(List<DataSourceColumnModel> columnInfoList, List<Map<String, Object>> rtnDataList) throws Exception {
		for (Map<String, Object> rtnData: rtnDataList) {
			convertDataResult(columnInfoList, rtnData);
		}
		return rtnDataList;
	}

	private Map<String, Object> convertDataResult(List<DataSourceColumnModel> columnInfoList, Map<String, Object> rtnData) throws Exception {
		for (DataSourceColumnModel columnInfo : columnInfoList) {
			if (((PageDataSourceColumnModel)columnInfo).getColumnAddon().containsKey("dataEncode")) {
				Object dataEncode = ((PageDataSourceColumnModel)columnInfo).getColumnAddon().get("dataEncode");
				if ("1".equals(dataEncode)) {
					String strValue = CommonUtil.toString(rtnData.get(columnInfo.getColumnId()));
					strValue = EncryptorUtil.encryptString(strValue, websiteProp.getDesSalt());
					rtnData.put(columnInfo.getColumnId(), strValue);
				} else if ("2".equals(dataEncode)) {
					String strValue = CommonUtil.toString(rtnData.get(columnInfo.getColumnId()));
					String[] pageInfo = strValue.substring(1).split("/");
					String groupId = EncryptorUtil.encryptString(pageInfo[0], websiteProp.getDesSalt());
					String pageId = EncryptorUtil.encryptString(pageInfo[1], websiteProp.getDesSalt());
					rtnData.put(columnInfo.getColumnId(), "/" + groupId + "/" + pageId);
				}
			}
		}
		return rtnData;
	}

	@SuppressWarnings("unchecked")
	private void convertLinkData(List<PageDataSourceColumnModel> columnInfoList, Map<String, Object> resultData,
			ProcessDto proceeDto, List<Object> resultList, Map<String, Object> srcAddonMap) {

		// 关联数据源值取得
		for (PageDataSourceColumnModel columnInfo : columnInfoList) {
			if (StringUtils.isEmpty(columnInfo.getLinkDatasourceIndex())) {
				continue;
			}

			try {
				ObjectMapper objectMapper = new ObjectMapper();

				Map<String, Object> subCondition = new HashMap<String, Object>();

				HashMap<String, String> jCondition = objectMapper.readValue(columnInfo.getLinkCondition(),
						HashMap.class);
				for (String key : jCondition.keySet()) {
					String keyValue = jCondition.get(key);
					if (keyValue.startsWith("'") && keyValue.endsWith("'")) {
						keyValue = keyValue.substring(1, keyValue.length() - 1);
					} else if (resultData.containsKey(keyValue) && resultData.get(keyValue) != null) {
						keyValue = CommonUtil.toString(resultData.get(keyValue));
					}
					if (StringUtils.isEmpty(keyValue)) {
						keyValue = "__null__";
					}
					keyValue = DataUtil.getProceeValue(keyValue, proceeDto, resultList, versionHolder.getLastVersion());
					subCondition.put(key, keyValue);
				}

				Object linkData = searchLinkData(columnInfo.getLinkDatasourceIndex(), subCondition, proceeDto,
						resultList);
				resultData.put(columnInfo.getLinkColumnId(), linkData);
			} catch (Exception e) {
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private Object searchLinkData(String datasourceIndex, Map<String, Object> searchCondition,
			ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 画面组ID
		String pageGroupId = EncryptorUtil.decryptString(proceeDto.getStringData1(), websiteProp.getDesSalt());

		// 画面ID
		String pageId = EncryptorUtil.decryptString(proceeDto.getStringData2(), websiteProp.getDesSalt());

		// 页面数据源信息
		PageDataSourceModel pageDataSourceParam = new PageDataSourceModel();
		pageDataSourceParam.setGroupId(pageGroupId);
		pageDataSourceParam.setPageId(pageId);
		pageDataSourceParam.setDatasourceIndex(datasourceIndex);
		PageDataSourceModel dataSourceInfo = pageDataSourceDao.findPageDataSource(pageDataSourceParam);
		if (dataSourceInfo == null) {
			logger.error("页面数据源信息未找到。");
			throw new FacadeException("s.common.error.none");
		}

		// 数据源附加信息
		Map<String, Object> addonMap = dataSourceInfo.getDatasourceAddon();
		addonMap.put("alwaysEquals", "1");

		// 数据源初期条件
		if (StringUtils.hasText(dataSourceInfo.getSearchCondition())
				&& (addonMap.containsKey("init") == false
						|| !CommonUtil.equals(CommonUtil.toString(addonMap.get("init")), "0"))) {
			if (proceeDto.getObjData1() == null) {
				proceeDto.setObjData1(new HashMap<String, Object>());
			}

			JSONObject initCondition = new JSONObject(dataSourceInfo.getSearchCondition());
			Iterator names = initCondition.keys();
			while (names.hasNext()) {
				String name = CommonUtil.toString(names.next());
				if (proceeDto.getObjData1().containsKey(name)) {
					continue;
				}
				proceeDto.getObjData1().put(name, initCondition.get(name));
			}
		}

		// 数据源列信息
		PageDataSourceColumnModel dataSourceColumnParam = new PageDataSourceColumnModel();
		dataSourceColumnParam.setGroupId(pageGroupId);
		dataSourceColumnParam.setPageId(pageId);
		dataSourceColumnParam.setDatasourceIndex(datasourceIndex);
		List<PageDataSourceColumnModel> columnInfoList = pageDataSourceColumnDao
				.searchDataSourceColumn(dataSourceColumnParam);

		// 单条检索
		if ("0".equals(dataSourceInfo.getSearchType())) {
			return findData(dataSourceInfo, columnInfoList, searchCondition, addonMap,
					proceeDto, resultList);
		}

		// 全检索
		else if ("1".equals(dataSourceInfo.getSearchType())) {
			return searchDataList(dataSourceInfo, columnInfoList, searchCondition, addonMap,
					proceeDto, resultList);
		}

		return null;
	}
}
