/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstanceQuery;
import org.camunda.bpm.engine.history.HistoricVariableInstanceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.base.PaginationModel;
import we.base.dto.PaginationData;
import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.model.DataSourceColumnModel;
import we.website.model.DataSourceInfoModel;
import we.website.service.DataSourceColumnService;
import we.website.service.DataSourceInfoService;

/**
 * 数据申请状态检索
 *
 * @author cp0
 * @since 0.0
 */
@Deprecated
@Component("dsApplyStatusProcess")
public class ApplyStatusProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HistoryService historyService;

	@Autowired
	private DataSourceInfoService dataSourceInfoService;

	@Autowired
	private DataSourceColumnService dataSourceColumnService;

//	@Autowired
//	private PageDataSearchProcess pageDataSearchProcess;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 数据源ID
		String datasourceId = proceeDto.getStringData1();
		if (StringUtils.hasText(datasourceId) == false) {
			logger.error("数据源ID未输入。");
			throw new FacadeException("s.common.error.param");
		}

		// 数据源信息
		DataSourceInfoModel dataSourceInfo = dataSourceInfoService.findDataSource(datasourceId);
		if (dataSourceInfo == null) {
			logger.error("数据源信息未找到。");
			throw new FacadeException("s.common.error.param");
		}

		// 数据源列信息
		DataSourceColumnModel dataSourceColumnParam = new DataSourceColumnModel();
		dataSourceColumnParam.setDatasourceId(dataSourceInfo.getDatasourceId());
		List<DataSourceColumnModel> columnInfoList = dataSourceColumnService.searchDataSourceColumn(dataSourceColumnParam);

		// 分页信息
		Map<String, Object> pagingParam = proceeDto.getObjData2();

		// 分页信息设定
		PaginationModel pagingModel = new PaginationModel();
		if (pagingParam != null) {
			if (pagingParam.containsKey("pageNo")) {
				int pageNo = Integer.valueOf(pagingParam.get("pageNo").toString());
				pagingModel.setPageNo(pageNo);
			}

			if (pagingParam.containsKey("pageSize")) {
				int pageSize = Integer.valueOf(pagingParam.get("pageSize").toString());
				pagingModel.setPageSize(pageSize);
			}
		}

		// 流程任务检索
		HistoricProcessInstanceQuery processQuery = historyService.createHistoricProcessInstanceQuery();
		Map<String, Object> taskCondition = proceeDto.getObjData1();
		if (taskCondition != null) {
			String processName = CommonUtil.toString(taskCondition.get("processName"));
			if (StringUtils.hasText(processName)) {
				processQuery = processQuery.processDefinitionNameLike(processName);
			}

			String applyUser = CommonUtil.toString(taskCondition.get("apply_user"));
			if (StringUtils.hasText(applyUser)) {
				processQuery = processQuery.variableValueLike("apply_user", applyUser);
			}
		}

		Map<String, Object> variableCondition = proceeDto.getObjData3();
		if (variableCondition != null) {
			for (Map.Entry<String, Object> varData : variableCondition.entrySet()) {
				String varValue = CommonUtil.toString(varData.getValue());
				if (StringUtils.hasText(varValue)) {
					processQuery = processQuery.variableValueLike(varData.getKey(), varValue);
				}
			}
		}

		processQuery = processQuery.orderByProcessInstanceStartTime().desc();

		// 件数
		int itemCount = Long.valueOf(processQuery.count()).intValue();
		pagingModel.setItemCount(itemCount);
		if (pagingModel.getPageNo() > pagingModel.getPageTotalCount()) {
			pagingModel.setPageNo(1);
		}

		// 数据取得
		List<HistoricProcessInstance> instanceList = processQuery.listPage(pagingModel.getStartNum(),
				pagingModel.getPageSize());

		// 数据一览
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (HistoricProcessInstance instanceInfo : instanceList) {
			Map<String, Object> resultData = new HashMap<String, Object>();
			dataList.add(resultData);

			HistoricTaskInstance taskInstance = null;

			// 项目取得
			for (DataSourceColumnModel columnInfo : columnInfoList) {
//				// 关联数据源
//				if (StringUtils.hasText(columnInfo.getLinkDatasourceId())) {
//					continue;
//				}

				// 属性值取得
				Object propVal;
				if (columnInfo.getColumnId().startsWith("task_")) {
					if (taskInstance == null) {
						HistoricTaskInstanceQuery historicQuery = historyService.createHistoricTaskInstanceQuery();
						historicQuery = historicQuery.processInstanceId(instanceInfo.getId());
						historicQuery = historicQuery.orderByHistoricActivityInstanceStartTime().desc();

						//taskInstance = historicQuery.singleResult();
						taskInstance = historicQuery.list().get(0);
					}

					String[] columnKeys = columnInfo.getColumnId().split("_", 2);
					propVal = PropertyUtils.getProperty(taskInstance, columnKeys[columnKeys.length - 1]);
				} else if (columnInfo.getColumnId().startsWith("var_")) {
					String[] columnKeys = columnInfo.getColumnId().split("_", 2);
					HistoricVariableInstanceQuery historicQuery = historyService.createHistoricVariableInstanceQuery();
					historicQuery = historicQuery.processInstanceId(instanceInfo.getId());
					historicQuery = historicQuery.variableName(columnKeys[columnKeys.length - 1]);
					propVal = CommonUtil.toString(historicQuery.list().get(0).getValue());

				} else {
					propVal = PropertyUtils.getProperty(instanceInfo, columnInfo.getColumnId());
				}
				resultData.put(columnInfo.getColumnId(), propVal);
			}

			// 关联数据源
			convertLinkData(columnInfoList, resultData, proceeDto, resultList);
		}

		// 返回结果
		PaginationData paginationData = new PaginationData();
		paginationData.setPageInfo(pagingModel);
		paginationData.setDataList(dataList);
		return paginationData;
	}

//	@SuppressWarnings("unchecked")
	private void convertLinkData(List<DataSourceColumnModel> columnInfoList, Map<String, Object> resultData, ProcessDto proceeDto, List<Object> resultList) {
//
//		// 附加信息
//		Map<String, Object> addonMap = new HashMap<String, Object>();
//		addonMap.put("alwaysEquals", "1");
//
//		// 关联数据源值取得
//		for (DataSourceColumnModel columnInfo : columnInfoList) {
//			if (StringUtils.isEmpty(columnInfo.getLinkDatasourceId())) {
//				continue;
//			}
//
//			try {
//				ObjectMapper objectMapper = new ObjectMapper();
//
//				Map<String, Object> subCondition = new HashMap<String, Object>();
//
//				HashMap<String, String> jCondition = objectMapper.readValue(columnInfo.getLinkCondition(), HashMap.class);
//				for (String key : jCondition.keySet()) {
//					String keyValue = jCondition.get(key);
//					if (keyValue.startsWith("'") && keyValue.endsWith("'")) {
//						keyValue = keyValue.substring(1, keyValue.length() - 1);
//					} else if (resultData.containsKey(keyValue) && resultData.get(keyValue) != null) {
//						keyValue = CommonUtil.toString(resultData.get(keyValue));
//					}
//					if (StringUtils.isEmpty(keyValue)) {
//						keyValue = "__null__";
//					}
//					keyValue = DataUtil.getProceeValue(keyValue, proceeDto, resultList);
//					subCondition.put(key, keyValue);
//				}
//
//				Object linkData = pageDataSearchProcess.searchData(columnInfo.getLinkDatasourceId(), columnInfo.getLinkType(), subCondition, null, null, addonMap, proceeDto, resultList);
//				resultData.put(columnInfo.getColumnId(), linkData);
//			} catch (Exception e) { }
//		}
	}

}
