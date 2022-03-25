/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.bpmn;

import java.util.List;

import org.camunda.bpm.engine.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;

/**
 * 流程部属删除
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class BpmnDeployDeleteProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RepositoryService repositoryService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 流程定义ID
		String processDefinitionId = proceeDto.getStringData1();
		if (StringUtils.isEmpty(processDefinitionId)) {
			logger.error("部属ID不能为空");
			throw new FacadeException("s.common.error.param");
		}

		// 删除流程定义
		repositoryService.deleteProcessDefinition(processDefinitionId, true);
		return null;
	}

}
