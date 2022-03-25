/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.StartFormData;
import org.camunda.bpm.engine.repository.ProcessDefinitionQuery;
import org.camunda.bpm.engine.runtime.ProcessInstance;
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

/**
 * 数据申请
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class ApplyInsertProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IdentityService identityService;

	@Autowired
	private RepositoryService repositoryService;

	@Autowired
	private FormService formService;

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private VersionHolder versionHolder;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 流程Key
		String processKey = proceeDto.getStringData1();
		if (StringUtils.hasText(processKey) == false) {
			logger.error("流程Key未输入。");
			throw new FacadeException("s.common.error.param");
		}

		Map<String,Object> dataMap = proceeDto.getObjData1();

		// 流程定义检索
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		processDefinitionQuery = processDefinitionQuery.processDefinitionKey(processKey);
		String processDefinitionId = processDefinitionQuery.latestVersion().singleResult().getId();

		// 流程变量
		Map<String, Object> variables = new HashMap<String, Object>();

		if (dataMap != null) {
			StartFormData startFormData = formService.getStartFormData(processDefinitionId);
			for (FormField formField : startFormData.getFormFields()) {
				String fieldId = formField.getId();

				String strValue = CommonUtil.toString(dataMap.get(fieldId));
				strValue = DataUtil.getProceeValue(strValue, proceeDto, resultList, versionHolder.getLastVersion());
				variables.put(fieldId, strValue);
			}
		}

		// 操作者ID
		String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
		identityService.setAuthenticatedUserId(loginName);

		// 流程启动
		ProcessInstance ins = runtimeService.startProcessInstanceById(processDefinitionId, variables);
		return ins.getId();
	}
}
