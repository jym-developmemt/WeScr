/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.bpmn;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.core.CoreProperties;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;

/**
 * 更新BPMN模型
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class BpmnModelUpdateProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CoreProperties websiteProp;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 资源ID
		String resourceId = proceeDto.getStringData1();
		if (StringUtils.isEmpty(resourceId)) {
			logger.error("资源ID不能为空");
			throw new FacadeException("s.common.error.param");
		}

		// 资源名称
		String resourceName = resourceId + ".bpmn20.xml";

		// XML数据
		String xmlData = proceeDto.getStringData2();

		// 文件更新
		String targetFilePath = CommonUtil.convertToAbsolutePath(websiteProp.getUploadPath() + "\\bpmn\\" + resourceName);
		File targetFile = new File(targetFilePath);
		FileUtils.write(targetFile, xmlData, "UTF-8");

		return null;
	}

}
