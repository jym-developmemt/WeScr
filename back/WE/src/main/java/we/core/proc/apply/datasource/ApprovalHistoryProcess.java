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
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.history.HistoricTaskInstanceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
@Component("dsApprovalHistoryProcess")
public class ApprovalHistoryProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HistoryService historyService;

//	@Autowired
//	private PageDataSearchProcess pageDataSearchProcess;

	@Autowired
	private DataSourceInfoService dataSourceInfoService;

	@Autowired
	private DataSourceColumnService dataSourceColumnService;

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

		// 流程任务检索
		HistoricTaskInstanceQuery historicQuery = historyService.createHistoricTaskInstanceQuery();
		Map<String, Object> taskCondition = proceeDto.getObjData1();
		if (taskCondition != null) {
			String processInstanceId = CommonUtil.toString(taskCondition.get("processInstanceId"));
			if (StringUtils.hasText(processInstanceId)) {
				historicQuery = historicQuery.processInstanceId(processInstanceId);
			}

			String taskName = CommonUtil.toString(taskCondition.get("taskName"));
			if (StringUtils.hasText(taskName)) {
				historicQuery = historicQuery.taskNameLike(taskName);
			}

			String applyUser = CommonUtil.toString(taskCondition.get("apply_user"));
			if (StringUtils.hasText(applyUser)) {
				historicQuery = historicQuery.processVariableValueLike("apply_user", applyUser);
			}

			String taskOwner = CommonUtil.toString(taskCondition.get("task_owner"));
			if (StringUtils.hasText(taskOwner)) {
				historicQuery = historicQuery.taskOwnerLike(taskOwner);
			}
		}

		Map<String, Object> variableCondition = proceeDto.getObjData3();
		if (variableCondition != null) {
			for (Map.Entry<String, Object> varData : variableCondition.entrySet()) {
				String varValue = CommonUtil.toString(varData.getValue());
				if (StringUtils.hasText(varValue)) {
					historicQuery = historicQuery.processVariableValueLike(varData.getKey(), varValue);
				}
			}
		}

		historicQuery = historicQuery.orderByHistoricActivityInstanceStartTime().asc();


		// 数据取得
		List<HistoricTaskInstance> taskList = historicQuery.list();

		// 数据一览
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (HistoricTaskInstance taskInfo : taskList) {
			Map<String, Object> resultData = new HashMap<String, Object>();
			dataList.add(resultData);

			// 项目取得
			for (DataSourceColumnModel columnInfo : columnInfoList) {
//				// 关联数据源
//				if (StringUtils.hasText(columnInfo.getLinkDatasourceId())) {
//					continue;
//				}

				// 属性值取得
				Object propVal = PropertyUtils.getProperty(taskInfo, columnInfo.getColumnId());
				resultData.put(columnInfo.getColumnId(), propVal);
			}

			// 关联数据源
			convertLinkData(columnInfoList, resultData, proceeDto, resultList);
		}

		// 返回结果
		return dataList;
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
