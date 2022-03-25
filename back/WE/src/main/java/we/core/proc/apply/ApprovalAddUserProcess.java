/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply;

import java.util.List;

import org.camunda.bpm.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;

/**
 * 审批移送
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class ApprovalAddUserProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TaskService taskService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 申请实例ID
		String taskId = proceeDto.getStringData1();
		if (StringUtils.hasText(taskId) == false) {
			logger.error("申请实例ID未输入。");
			throw new FacadeException("s.common.error.param");
		}

		// 审批用户ID
		String userId = proceeDto.getStringData2();
		if (StringUtils.hasText(userId) == false) {
			logger.error("审批用户ID未输入。");
			throw new FacadeException("s.common.error.param");
		}

		taskService.addCandidateUser(taskId, userId);
		return null;
	}

}
