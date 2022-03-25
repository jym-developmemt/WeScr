/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply;

import java.util.List;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.camunda.bpm.engine.runtime.EventSubscriptionQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;

/**
 * 申请实例删除
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class ProcessSignalProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RuntimeService runtimeService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 申请实例ID
		String instanceId = proceeDto.getStringData1();
		if (StringUtils.hasText(instanceId) == false) {
			logger.error("申请实例ID未输入。");
			throw new FacadeException("s.common.error.param");
		}
		// 信号名称
		String signalName = proceeDto.getStringData2();
		if (StringUtils.hasText(signalName) == false) {
			logger.error("信号名称未输入。");
			throw new FacadeException("s.common.error.param");
		}

		// 发送信号
		EventSubscriptionQuery query = runtimeService.createEventSubscriptionQuery().processInstanceId(instanceId).eventName(signalName);
		if (query.count() == 0) {
			logger.error("未找到信息");
			throw new FacadeException("s.common.error.none");
		}

		for (EventSubscription eventSubscription : query.list()) {
			runtimeService.signalEventReceived(signalName, eventSubscription.getExecutionId());
		}
		return null;
	}

}
