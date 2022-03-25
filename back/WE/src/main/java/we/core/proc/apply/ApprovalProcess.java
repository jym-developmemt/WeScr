/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.VariableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.base.util.DataUtil;
import we.base.util.VersionHolder;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.core.proc.com.ApprovalUtil;

/**
 * 完成审批节点
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class ApprovalProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IdentityService identityService;

	@Autowired
	private TaskService taskService;

	@Autowired
	private FormService formService;

	@Autowired
	private VersionHolder versionHolder;
	
	@Autowired
	private ApprovalUtil approvalUtil;
	
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 申请实例ID
		String taskId = proceeDto.getStringData1();
		if (StringUtils.hasText(taskId) == false) {
			logger.error("申请实例ID未输入。");
			throw new FacadeException("s.common.error.param");
		}

		// 批注
		String comment = proceeDto.getStringData2();

		// 操作者ID
		String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
		identityService.setAuthenticatedUserId(loginName);

		Map<String, Object> dataMap = proceeDto.getObjData1();
		Map<String, Object> variables = proceeDto.getObjData2();

		if (proceeDto.getListData1() == null) {
			// 流程变量
			if (variables == null) {
				variables = new HashMap<String, Object>();
			}
			if (dataMap != null) {
				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
				TaskFormData taskFormData = formService.getTaskFormData(task.getId());
				for (FormField formField : taskFormData.getFormFields()) {
					String fieldId = formField.getId();

					String strValue = CommonUtil.toString(dataMap.get(fieldId));
					strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
					variables.put(fieldId, strValue);
				}
			}
			if (StringUtils.hasText(comment)) {
				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
				taskService.createComment(taskId, task.getProcessInstanceId(), comment);
			}

			try {
				taskService.claim(taskId, loginName);
				try {
					if (dataMap != null) {
						Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
						
						Map<String, Object> param = new HashMap<String, Object>();
						
						//当前步骤番号和名称
						int stepIndex = Integer.parseInt(CommonUtil.toString(taskService.getVariable(taskId, "step_index")));
						String stepName = CommonUtil.toString(taskService.getVariable(taskId, "step_name"));
						//生成
						approvalUtil.createFlowHisData(proceeDto, task.getProcessInstanceId(), taskId, stepIndex, stepName);
					}
				} catch (Exception e) {
					throw new FacadeException("审批历史保存失败 -- 查看 B17单条审批 : ApprovalProcess"); 
				}
				
				return taskService.completeWithVariablesInReturn(taskId, variables, true);
			} catch (Exception e) {
				if (e.getCause() instanceof FacadeException) {
					throw (FacadeException) e.getCause();
				}
				throw e;
			}
		} else {
			List<VariableMap> rtnList = new ArrayList<VariableMap>();

			for (Map<String, Object> processMap : proceeDto.getListData1()) {
				String taskIdVal = CommonUtil.toString(processMap.get(taskId));

				// 流程变量
				if (variables == null) {
					variables = new HashMap<String, Object>();
				}
				if (dataMap != null) {
					Task task = taskService.createTaskQuery().taskId(taskIdVal).singleResult();
					TaskFormData taskFormData = formService.getTaskFormData(task.getId());
					for (FormField formField : taskFormData.getFormFields()) {
						String fieldId = formField.getId();

						String strValue = CommonUtil.toString(dataMap.get(fieldId));
						strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
						variables.put(fieldId, strValue);
					}
				}
				if (StringUtils.hasText(comment)) {
					Task task = taskService.createTaskQuery().taskId(taskIdVal).singleResult();
					taskService.createComment(taskIdVal, task.getProcessInstanceId(), comment);
				}

				try {
					taskService.claim(taskIdVal, loginName);
					try {
						if (dataMap != null) {
							Task task = taskService.createTaskQuery().taskId(taskIdVal).singleResult();
							
							//当前步骤番号和名称
							int stepIndex = Integer.parseInt(CommonUtil.toString(taskService.getVariable(taskId, "step_index")));
							String stepName = CommonUtil.toString(taskService.getVariable(taskId, "step_name"));
							
							approvalUtil.createFlowHisData(proceeDto, task.getProcessInstanceId(), taskId, stepIndex, stepName);
						}
					} catch (Exception e) {
						throw new FacadeException("审批历史保存失败 -- 查看 B17 批量审批: ApprovalProcess"); 
					}
					rtnList.add(taskService.completeWithVariablesInReturn(taskIdVal, variables, true));
				} catch (Exception e) {
					if (e.getCause() instanceof FacadeException) {
						throw (FacadeException) e.getCause();
					}
					throw e;
				}
			}

			return rtnList;
		}
	}
	
}
