/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply.simple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.DataUtil;
import we.base.util.VersionHolder;
import we.core.dto.ProcessDto;
import we.core.model.ApplyFlowModel;
import we.core.model.ApplyInfoModel;
import we.core.model.ConditionInfoModel;
import we.core.model.FlowConditionModel;
import we.core.proc.IProcess;
import we.core.service.ApplyFlowService;
import we.website.model.UserInfoModel;

/**
 * 简单数据申请
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class SimpleApplyInsertProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IdentityService identityService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private ApplyFlowService applyFlowService;

	@Autowired
	private VersionHolder versionHolder;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 审批ID
		String applyId = proceeDto.getStringData1();
		if (StringUtils.hasText(applyId) == false) {
			logger.error("审批ID未输入。");
			throw new FacadeException("s.common.error.param");
		}

		// 流程Key
		String processKey = proceeDto.getStringData2();
		if (StringUtils.hasText(processKey) == false) {
			processKey = "SimpleProcess";
		}

		// 操作者ID
		Authentication loginUser = SecurityContextHolder.getContext().getAuthentication();

		// 申请部门
		String applyDepartment = proceeDto.getStringData3();
		if (StringUtils.hasText(applyDepartment) == false) {
			UserInfoModel userInfo = (UserInfoModel)loginUser.getPrincipal();
			applyDepartment = userInfo.getDepartmentId();
		}
		if (StringUtils.hasText(applyDepartment) == false) {
			logger.error("申请者部门未找到。");
			throw new FacadeException("s.common.error.param");
		}

		// 审批流程信息
		ApplyInfoModel applyInfo = applyFlowService.findApplyInfo(applyId);

		// 流程变量
		Map<String, Object> variables = new HashMap<String, Object>();
		// 流程条件信息检索
		List<ConditionInfoModel> conditionList = applyFlowService.searchConditionInfo(applyId);
		for (ConditionInfoModel conditionInfo : conditionList) {
			// 条件ID
			String conditionId = conditionInfo.getConditionId();
			// 条件类型
			String itemType = conditionInfo.getItemType();
			// 条件项目KEY
			String itemKey = conditionInfo.getItemKey();
			// 取得值
			String itemValue = "";
			if (CommonUtil.equals(itemType, "0")) {
				if (CommonUtil.equals(itemKey, "apply_user")) {
					itemValue = loginUser.getName();
				} else if (CommonUtil.equals(itemKey, "apply_department")) {
					itemValue = applyDepartment;
				} else if (CommonUtil.equals(itemKey, "apply_date")) {
					itemValue = CommonUtil.getSystemDate("yyyyMMdd");
				} else if (CommonUtil.equals(itemKey, "apply_datetime")) {
					itemValue = CommonUtil.getSystemDate("yyyyMMddHHmmss");
				}
			} else if (CommonUtil.equals(itemType, "1")) {
				itemValue = DataUtil.getProceeValue("$o1." + itemKey, proceeDto, resultList, versionHolder.getLastVersion());
				itemValue = DataUtil.convertDefaultValue(itemValue, null);
			}
			variables.put("UserData" + conditionId, itemValue);
		}

		// 审批流程条件检索
		List<FlowConditionModel> flowConditionList = applyFlowService.searchFlowCondition(applyId);
		int curflowIndex = 1;
		int flowIndex = 0;
		int loopIndex = 0;
		for (FlowConditionModel flowCondition : flowConditionList) {
			loopIndex++;

			if (flowCondition.getFlowIndex() < curflowIndex) {
				continue;
			}
			if (flowCondition.getFlowIndex() > curflowIndex) {
				flowIndex = curflowIndex;
				break;
			}

			// 条件ID
			String conditionId = flowCondition.getConditionId();
			// 变量值
			String variableValue = CommonUtil.toString(variables.get("UserData" + conditionId));
			// 条件类型
			String itemType = flowCondition.getConditionType();
			// 条件值
			String conditionValue = flowCondition.getConditionValue();

			// 相等
			if (CommonUtil.equals(itemType, "0")) {
				if (CommonUtil.equals(variableValue, conditionValue) == false) {
					curflowIndex++;
					continue;
				}
			}
			// 部分
			if (CommonUtil.equals(itemType, "1")) {
				if (variableValue.indexOf(conditionValue) == -1) {
					curflowIndex++;
					continue;
				}
			}
			// 小于
			if (CommonUtil.equals(itemType, "4")) {
				if (StringUtils.isEmpty(variableValue)) {
					continue;
				}

				try {
					double varVal = Double.parseDouble(variableValue);
					double conVal = Double.parseDouble(conditionValue);
					if (varVal >= conVal) {
						curflowIndex++;
						continue;
					}
				} catch (Exception e) {
					curflowIndex++;
					continue;
				}
			}
			// 大于
			if (CommonUtil.equals(itemType, "5")) {
				if (StringUtils.isEmpty(variableValue)) {
					continue;
				}

				try {
					double varVal = Double.parseDouble(variableValue);
					double conVal = Double.parseDouble(conditionValue);
					if (varVal <= conVal) {
						curflowIndex++;
						continue;
					}
				} catch (Exception e) {
					curflowIndex++;
					continue;
				}
			}
		}

		if (flowConditionList.size() > 0) {
			// 符合最终条件时
			if (loopIndex == flowConditionList.size()) {
				int lastFlowIndex = flowConditionList.get(flowConditionList.size() - 1).getFlowIndex();
				if (lastFlowIndex == curflowIndex) {
					flowIndex = curflowIndex;
				}
			}
		}

		// 审批流程信息检索
		ApplyFlowModel applyFlow = new ApplyFlowModel();
		applyFlow.setApplyId(applyId);
		applyFlow.setFlowIndex(flowIndex);
		List<ApplyFlowModel> applyFlowList = applyFlowService.searchApplyFlow(applyFlow);

		// 流程变量初始化
		variables.put("apply_id", applyId);
		variables.put("apply_department_id", applyDepartment);
		variables.put("approval_department_id", applyDepartment);
		variables.put("flow_index", flowIndex);
		variables.put("step_index", 0);
		variables.put("max_step", applyFlowList.size());
		variables.put("approval_result", "0");
		variables.put("approval_status", "5");
		variables.put("apply_display_date", CommonUtil.getSystemDate("yyyy/MM/dd"));
		variables.put("apply_display_datetime", CommonUtil.getSystemDate("yyyy/MM/dd HH:mm"));
		variables.put("EMPTY_ARRAY", new ArrayList<String>());
		variables.put("apply_user_name", ((UserInfoModel) loginUser.getPrincipal()).getUserName());

		// 标题
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("applyInfo", applyInfo);
		dataMap.put("applyUser", loginUser.getPrincipal());
		dataMap.put("applyData", variables);
		dataMap.put("userData", proceeDto.getObjData1());
		// 标题
		variables.put("apply_name", DataUtil.convertPathValue(applyInfo.getTitleExpression(), dataMap));
		// 说明
		variables.put("apply_comment", DataUtil.convertPathValue(applyInfo.getCommentExpression(), dataMap));

		// 申请者ID
		identityService.setAuthenticatedUserId(loginUser.getName());

		// 启动流程
		ProcessInstance ins = runtimeService.startProcessInstanceByKey(processKey, variables);
		return ins.getId();
	}
}
