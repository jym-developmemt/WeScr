/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.apply.simple;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import we.base.exception.FacadeException;

/**
 * 最终更新前处理
 *
 * @author cp0
 * @since 0.0
 */
public interface BeforeEndProcess {

	/**
	 * 处理实行
	 */
	public void execute(DelegateExecution execution) throws FacadeException;
}
