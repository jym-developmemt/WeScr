/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricProcessInstance;
import org.camunda.bpm.engine.history.HistoricProcessInstanceQuery;
import org.camunda.bpm.engine.history.HistoricVariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.base.PaginationModel;
import we.base.dto.PaginationData;
import we.base.util.CommonUtil;
import we.base.util.DataUtil;
import we.base.util.VersionHolder;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;

/**
 * 流程实例检索
 *
 * @author cp0
 * @since 0.0
 */
@Component()
public class ProcessSearchProcess implements IProcess {

	@Autowired
	private HistoryService historyService;

	@Autowired
	private VersionHolder versionHolder;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 检索条件
		Map<String, Object> condition = proceeDto.getObjData1();

		// 分页信息
		Map<String, Object> pagingParam = proceeDto.getObjData2();

		// 检索方式
		Map<String, Object> conditiontype = proceeDto.getObjData3();

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

		// 流程检索
		HistoricProcessInstanceQuery processQuery = historyService.createHistoricProcessInstanceQuery();

		// 检索条件
		if (condition != null) {
			for (String key : condition.keySet()) {
				// 检索方式
				String searchType = "";
				if (conditiontype != null) {
					searchType = CommonUtil.toString(conditiontype.get(key));
				}
				if (StringUtils.hasText(searchType) == false) {
					searchType = "0";
				}

				// 条件值
				String condValue = CommonUtil.toString(condition.get(key));
				if (StringUtils.isEmpty(condValue)) {
					continue;
				}
				condValue = DataUtil.getProceeValue(condValue, proceeDto, resultList, versionHolder.getLastVersion());

				if (CommonUtil.equals(searchType, "0")) {
					processQuery = processQuery.variableValueEquals(key, condValue);
				} else if (CommonUtil.equals(searchType, "1")) {
					processQuery = processQuery.variableValueLike(key, "%" + condValue + "%");
				} else if (CommonUtil.equals(searchType, "2")) {
					processQuery = processQuery.variableValueLike(key, condValue + "%");
				} else if (CommonUtil.equals(searchType, "3")) {
					processQuery = processQuery.variableValueNotEquals(key, condValue);
				} else if (CommonUtil.equals(searchType, "4")) {
					processQuery = processQuery.variableValueLessThan(key, condValue);
				} else if (CommonUtil.equals(searchType, "5")) {
					processQuery = processQuery.variableValueGreaterThan(key, condValue);
				} else if (CommonUtil.equals(searchType, "6")) {
					processQuery = processQuery.variableValueLessThanOrEqual(key, condValue);
				} else if (CommonUtil.equals(searchType, "7")) {
					processQuery = processQuery.variableValueGreaterThanOrEqual(key, condValue);
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
		List<HistoricProcessInstance> processList = processQuery.listPage(pagingModel.getStartNum(),
				pagingModel.getPageSize());

		// 数据一览
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (HistoricProcessInstance processInfo : processList) {
			// 数据
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataList.add(dataMap);

			// 流程实例ID
			String id = processInfo.getId();
			dataMap.put("instance_id", id);
			// 流程实例创建时间
			Date startTime = processInfo.getStartTime();
			dataMap.put("start_date", startTime);
			// 流程实例结束时间
			Date endTime = processInfo.getEndTime();
			dataMap.put("end_date", endTime);

			// 流程变量
			List<HistoricVariableInstance> variableList =  historyService.createHistoricVariableInstanceQuery().processInstanceId(id).list();
			for (HistoricVariableInstance variableInfo : variableList) {
				dataMap.put(variableInfo.getName(), variableInfo.getValue());
			}
		}

		// 返回结果
		PaginationData paginationData = new PaginationData();
		paginationData.setPageInfo(pagingModel);
		paginationData.setDataList(dataList);
		return paginationData;
	}

}
