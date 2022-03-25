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
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
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
 * 数据申请检索
 *
 * @author cp0
 * @since 0.0
 */
@Deprecated
@Component("dsProcessSearchProcess")
public class ProcessSearchProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TaskService taskService;

	@Autowired
	private RepositoryService repositoryService;

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
		TaskQuery taskQuery = taskService.createTaskQuery();

		Map<String, Object> taskCondition = proceeDto.getObjData1();
		if (taskCondition != null) {
			String processInstanceId = CommonUtil.toString(taskCondition.get("processInstanceId"));
			if (StringUtils.hasText(processInstanceId)) {
				taskQuery = taskQuery.processInstanceId(processInstanceId);
			}

			String userId = CommonUtil.toString(taskCondition.get("userId"));
			if (StringUtils.hasText(userId)) {
				taskQuery = taskQuery.processVariableValueLike("apply_user", "%" + userId + "%");
			}

			String taskName = CommonUtil.toString(taskCondition.get("taskName"));
			if (StringUtils.hasText(taskName)) {
				taskQuery = taskQuery.taskNameLike("%" + taskName + "%");
			}
		}

		taskQuery = taskQuery.active().orderByTaskCreateTime().desc();

		// 件数
		int itemCount = Long.valueOf(taskQuery.count()).intValue();
		pagingModel.setItemCount(itemCount);
		if (pagingModel.getPageNo() > pagingModel.getPageTotalCount()) {
			pagingModel.setPageNo(1);
		}

		// 数据取得
		List<Task> taskList = taskQuery.listPage(pagingModel.getStartNum(),
				pagingModel.getPageSize());

		// 数据一览
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (Task taskInfo : taskList) {
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

			// 申请者
			resultData.put("applyUserId", CommonUtil.toString(taskService.getVariable(taskInfo.getId(), "apply_user")));

			// 流程名称
			resultData.put("processName", repositoryService.createProcessDefinitionQuery().processDefinitionId(taskInfo.getProcessDefinitionId()).singleResult().getName());


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
