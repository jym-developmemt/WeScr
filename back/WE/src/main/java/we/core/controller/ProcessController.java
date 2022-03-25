/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import we.base.base.BaseController;
import we.base.dto.SendDto;
import we.base.exception.FacadeException;
import we.core.dto.ProcessDto;
import we.core.facade.ProcessFacade;

/**
 * 主处理Controller
 *
 * @author cp0
 * @since 0.0
 */
@RestController
@RequestMapping("/process")
public class ProcessController extends BaseController {

	/** 处理请求Facade */
	@Autowired
	private ProcessFacade processFacade;

	/**
	 * 处理请求
	 */
	@RequestMapping("/execute")
	public SendDto list(@RequestBody List<ProcessDto> processList) throws Exception {
		try {
			// 请求实行
			List<Object> resultList = processFacade.execute(processList);
			return successResult(resultList);
		} catch (FacadeException e) {
			logger.error("FacadeException", e);
			return errorResult(e.getMessage());
		} catch (Exception e) {
			logger.error("请求处理失败", e);
			return errorResult("s.common.error.process");
		}
	}
}
