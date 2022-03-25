/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply.simple;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.task.IdentityLink;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.DataUtil;
import we.core.dto.ProcessDto;
import we.core.model.SearchDataModel;
import we.core.service.PageDataSearchService;
import we.core.service.PageDataUpdateService;
import we.core.service.SearchDataService;
import we.website.model.DataSourceColumnModel;
import we.website.model.DataSourceInfoModel;
import we.website.service.DataSourceColumnService;
import we.website.service.DataSourceInfoService;

/**
 * 审批任务超时通过处理
 *
 * @author Brian
 * @since 0.0
 */
@Service
public class SimpleApprovalOvertimeDelegate implements JavaDelegate {

	@Autowired
	private DataSourceInfoService dataSourceInfoService;

	@Autowired
	private DataSourceColumnService dataSourceColumnService;

	@Autowired
	private SearchDataService searchDataService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private PageDataUpdateService pageDataUpdateService;

	@Autowired
	private PageDataSearchService pageDataSearchService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		String processInstanceId = execution.getProcessInstanceId();
		TaskQuery taskQuery = taskService.createTaskQuery();
		taskQuery.processInstanceId(processInstanceId);
		List<Task> taskList = taskQuery.list();

		for (Task task : taskList) {
			String assignee = task.getAssignee();
			this.approval(processInstanceId, task.getId(), assignee);
				if (StringUtils.hasText(assignee) == false) {
					List<IdentityLink> identityLinkList = taskService.getIdentityLinksForTask(task.getId());
					for (IdentityLink identityLink : identityLinkList) {
						if (StringUtils.hasText(identityLink.getGroupId())) {
							List<String> searchIdList = this.searchUserIdList(identityLink.getGroupId());
							for (String userId : searchIdList) {
								this.approval(processInstanceId, task.getId(), userId);
							}
						}
					}
				}
		}
		execution.setVariable("approval_result", "0");
	}

	public void approval(String processInstanceId, String taskId, String userId) throws Exception {
		// 申请实例ID
		if (StringUtils.hasText(taskId) == false) {
			throw new FacadeException("申请实例ID未输入。");
		}
		String commentValue = "超时自动审批";
		String status = "8";
		// 数据源信息
		DataSourceInfoModel dataSourceInfo = dataSourceInfoService.findDataSource("_approveComment");
		if (dataSourceInfo == null) {
			throw new FacadeException("数据源信息未找到。");
		}

		// 数据源列信息
		DataSourceColumnModel dataSourceColumnParam = new DataSourceColumnModel();
		dataSourceColumnParam.setDatasourceId(dataSourceInfo.getDatasourceId());
		List<DataSourceColumnModel> columnInfoList = dataSourceColumnService.searchDataSourceColumn(dataSourceColumnParam);

		// 流程变量
		Map<String, Object> variables = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();

		// 当前步骤番号
		int stepIndex = Integer.parseInt(CommonUtil.toString(taskService.getVariable(taskId, "step_index")));


		param.put("PROC_INST_ID_", processInstanceId);
		param.put("TASK_ID_", taskId);
		param.put("STEP_ID_", "STEP" + stepIndex);
		param.put("STEP_NAME_", CommonUtil.toString(taskService.getVariable(taskId, "step_name")));
		param.put("USER_ID_", userId);
		param.put("TIME_", null);
		param.put("MESSAGE_", commentValue);
		param.put("STATUS_", status);

		variables.put("approval_result", "0");

		insertData(dataSourceInfo, columnInfoList, param, null, null);

	}

	/**
	 * 数据插入处理
	 */
	private int insertData(DataSourceInfoModel dataSourceInfo, List<DataSourceColumnModel> columnInfoList,
			Map<String, Object> dataMap, ProcessDto proceeDto, List<Object> resultList) throws Exception {

//		// 附加信息
//		Map<String, Object> addonMap = new HashMap<String, Object>();
//		addonMap.put("alwaysEquals", "1");
//
//		// 主键信息
//		StringBuffer strPrimaryName = new StringBuffer();
//		List<DataSourceColumnModel> primaryList = new ArrayList<DataSourceColumnModel>();
//		for (DataSourceColumnModel columnInfo : columnInfoList) {
//			if (CommonUtil.equals(columnInfo.getPrimaryKey(), "1")) {
//				primaryList.add(columnInfo);
//
//				if (strPrimaryName.length() > 0) {
//					strPrimaryName.append(", ");
//				}
//				strPrimaryName.append(columnInfo.getDisplayName());
//
//				// 默认值变换
//				if (StringUtils.hasText(CommonUtil.toString(dataMap.get(columnInfo.getColumnId()))) == false) {
//					if (StringUtils.hasText(columnInfo.getDefaultValue())) {
//						dataMap.put(columnInfo.getColumnId(), DataUtil.convertDefaultValue(columnInfo.getDefaultValue(), dataMap));
//					}
//				}
//			}
//		}
//
//		// 件数校验
//		int cnt = pageDataSearchService.coundData(dataSourceInfo, primaryList, dataMap, addonMap, proceeDto, resultList);
//		if (cnt > 0) {
//			throw new FacadeException("数据重复，请更改 " + strPrimaryName + "。");
//		}
//
//		// 保留项目清除
//		dataMap.put("created_date", null);
//		dataMap.put("created_by", null);
//		dataMap.put("updated_date", null);
//		dataMap.put("updated_by", null);
//
//		// 数据插入
//		return pageDataUpdateService.insertData(dataSourceInfo, columnInfoList, dataMap, proceeDto, resultList);
		return 0;
	}

	/**
	 * 取得用户ID一览
	 */
	public List<String> searchUserIdList(String positionIds) throws Exception {
		List<String> userIdList = new ArrayList<String>();

		List<String> columnList = new ArrayList<String>();
		columnList.add("user_id");
		List<String> tableList = new ArrayList<String>();
		tableList.add("m_user_position");
		List<String> conditionList = new ArrayList<String>();

		// 用户ID表达式
		for (String positionExpression : positionIds.split(",")) {
			if (conditionList.size() == 0) {
				conditionList.add(" ( ");
			} else {
				conditionList.add(" or ");
			}

			String[] positionInfos = positionExpression.split(":");
			if (positionInfos.length == 1) {
				conditionList.add("department_id like '" + positionInfos[0] + "'");
			} else {
				if(StringUtils.hasText(positionInfos[0])) {
					conditionList.add("( department_id like '" + positionInfos[0] + "'");
					conditionList.add(" and position_id like '" + positionInfos[1] + "' )");
				} else {
					conditionList.add("  position_id like '" + positionInfos[1] + "' ");
				}
			}
		}
		conditionList.add(")");

		SimpleDateFormat dateFotmat = new SimpleDateFormat("yyyy/MM/dd");
		conditionList.add(" and start_date <= '" + dateFotmat.format(new Date()) + "'");
		conditionList.add(" and (end_date is null or end_date >= '" + dateFotmat.format(new Date()) + "')");

		SearchDataModel searchDataInfo = new SearchDataModel();
		searchDataInfo.setColumnList(columnList);
		searchDataInfo.setTableList(tableList);
		searchDataInfo.setConditionList(conditionList);

		// 用户一览查询
		List<Map<String, Object>> dataList = searchDataService.getSearchDataList(searchDataInfo);
		for (Map<String, Object> dataMap : dataList) {
			userIdList.add(CommonUtil.toString(dataMap.get("user_id")));
		}
		return userIdList;
	}

}
