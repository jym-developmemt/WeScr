/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply.simple;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import we.base.util.CommonUtil;

/**
 * 最终更新前处理（最终更新前监听器）
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class SimpleBeforeEndListener implements ExecutionListener {

	private static final String PROCESS_SUFFIX = "BeforeEndProcess";

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void notify(DelegateExecution execution) throws Exception {

		// 申请类型ID
		String applyId = CommonUtil.toString(execution.getVariable("apply_id"));
		if (StringUtils.isEmpty(applyId)) {
			return;
		}

		if (applicationContext.containsBean(applyId + PROCESS_SUFFIX) == false) {
			return;
		}

		// 处理实行
		BeforeEndProcess beforeEndProcess = applicationContext.getBean(applyId + PROCESS_SUFFIX,
				BeforeEndProcess.class);
		beforeEndProcess.execute(execution);
	}
}
