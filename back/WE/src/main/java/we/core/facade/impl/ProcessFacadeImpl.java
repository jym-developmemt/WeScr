/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.facade.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import we.base.base.BaseFacade;
import we.base.exception.FacadeException;
import we.core.CoreProperties;
import we.core.dto.ProcessDto;
import we.core.facade.ProcessFacade;
import we.core.proc.IProcess;
import we.core.proc.com.BreakProcess;

/**
 * 处理请求Facade
 *
 * @author cp0
 * @since 0.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProcessFacadeImpl extends BaseFacade implements ProcessFacade {

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * 处理实行
	 */
	@Override
	public List<Object> execute(List<ProcessDto> processList) throws Exception {
		// 结果列表
		List<Object> resultList = new ArrayList<Object>();

		// 同一事务下的全部处理实行
		for (ProcessDto executeInfo : processList) {

			// 取得处理Service名
			String processBean = websiteProp.getFunctions().get(executeInfo.getType());
			if (processBean == null) {
				logger.error("未识别的请求类型！(" + executeInfo.getType() + ")");
				throw new FacadeException("s.common.error.param");
			}

			// 取得处理Service
			IProcess process = applicationContext.getBean(processBean, IProcess.class);

			// 处理实行
			resultList.add(process.execute(executeInfo, resultList));

			// 处理中断
			if (process instanceof BreakProcess) {
				boolean result = (Boolean) resultList.get(resultList.size() - 1);
				if (result == true) {
					break;
				}
			}
		}

		return resultList;
	}

}
