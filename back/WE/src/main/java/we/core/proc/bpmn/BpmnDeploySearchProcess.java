/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.bpmn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.base.PaginationModel;
import we.base.dto.PaginationData;
import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;

/**
 * 发布BPMN模型
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class BpmnDeploySearchProcess implements IProcess {

	@Autowired
	private RepositoryService repositoryService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 检索条件
		Map<String, Object> searchCondition = proceeDto.getObjData1();

		// 流程KEY
		String searchKey = "";
		// 流程名称
		String searchName = "";
		if (searchCondition != null) {
			if (searchCondition.containsKey("key")) {
				searchKey = CommonUtil.toString(searchCondition.get("key"));
			}
			if (searchCondition.containsKey("name")) {
				searchName = CommonUtil.toString(searchCondition.get("name"));
			}
		}

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

		// 流程定义检索
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		if (StringUtils.hasText(searchKey)) {
			processDefinitionQuery = processDefinitionQuery.processDefinitionKeyLike("%" + searchKey + "%");
		}
		if (StringUtils.hasText(searchName)) {
			processDefinitionQuery = processDefinitionQuery.processDefinitionNameLike("%" + searchName + "%");
		}
		processDefinitionQuery = processDefinitionQuery.latestVersion().orderByProcessDefinitionKey().asc();

		// 件数
		int itemCount = Long.valueOf(processDefinitionQuery.count()).intValue();
		pagingModel.setItemCount(itemCount);
		if (pagingModel.getPageNo() > pagingModel.getPageTotalCount()) {
			pagingModel.setPageNo(1);
		}

		// 数据取得
		List<ProcessDefinition> processDefList = processDefinitionQuery.listPage(pagingModel.getStartNum(),
				pagingModel.getPageSize());

		// 数据一览
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (ProcessDefinition processDef : processDefList) {
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("category", processDef.getCategory());
			dataMap.put("deployment_id", processDef.getDeploymentId());
			dataMap.put("description", processDef.getDescription());
			dataMap.put("diagram_resource_name", processDef.getDiagramResourceName());
			dataMap.put("history_time_to_live", processDef.getHistoryTimeToLive());
			dataMap.put("id", processDef.getId());
			dataMap.put("key", processDef.getKey());
			dataMap.put("name", processDef.getName());
			dataMap.put("resource_name", processDef.getResourceName());
			dataMap.put("tenant_id", processDef.getTenantId());
			dataMap.put("version", processDef.getVersion());
			dataMap.put("version_tag", processDef.getVersionTag());
			dataList.add(dataMap);
		}

		// 返回结果
		PaginationData paginationData = new PaginationData();
		paginationData.setPageInfo(pagingModel);
		paginationData.setDataList(dataList);
		return paginationData;
	}

}
