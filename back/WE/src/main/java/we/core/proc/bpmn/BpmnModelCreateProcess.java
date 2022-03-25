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
import we.website.model.ResourceInfoModel;
import we.website.service.ResourceInfoService;

/**
 * 创建BPMN模型
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class BpmnModelCreateProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private ResourceInfoService resourceInfoService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 加载模板
		String templateName = "empty";
		if (StringUtils.hasText(proceeDto.getStringData1())) {
			templateName = proceeDto.getStringData1();
		}
		File templateFile = new File(CommonUtil.getClassPath() + "config\\bpmn\\" + templateName + ".bpmn20.xml");
		if (templateFile.exists() == false) {
			logger.error("未找到创建模板。");
			throw new FacadeException("s.common.error.param");
		}

		ResourceInfoModel resourceInfo = new ResourceInfoModel();
		resourceInfo.setResourceId(CommonUtil.generateKey());
		resourceInfo.setResourceName(resourceInfo.getResourceId() + ".bpmn20.xml");
		resourceInfo.setResourceType("2");
		resourceInfo.setResourcePath(websiteProp.getUploadPath() + "\\bpmn\\" + resourceInfo.getResourceName());
		resourceInfo.setTempFlg("1");
		resourceInfoService.addResourceInfo(resourceInfo);

		String targetFilePath = CommonUtil.convertToAbsolutePath(resourceInfo.getResourcePath());
		File targetFile = new File(targetFilePath);
		FileUtils.copyFile(templateFile, targetFile);

		return resourceInfo.getResourceId();
	}

}
