/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply.simple;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 审批用户通知（任务创建监听器）
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class SimpleBackCreateListener implements TaskListener {


	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void notify(DelegateTask delegateTask) {

	}
}
