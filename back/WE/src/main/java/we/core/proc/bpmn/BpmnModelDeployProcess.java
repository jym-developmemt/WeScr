/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.bpmn;

import java.io.FileInputStream;
import java.util.List;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.model.BpmnModel;
import we.core.proc.IProcess;
import we.core.service.BpmnModelService;
import we.website.model.ResourceInfoModel;
import we.website.service.ResourceInfoService;

/**
 * 发布BPMN模型
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class BpmnModelDeployProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BpmnModelService bpmnModelService;

	@Autowired
	private ResourceInfoService resourceInfoService;

	@Autowired
	private RepositoryService repositoryService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 模型ID
		String modelId = proceeDto.getStringData1();
		if (StringUtils.isEmpty(modelId)) {
			logger.error("模型ID不能为空");
			throw new FacadeException("s.common.error.param");
		}

		// BPMN模型信息取得
		BpmnModel bpmnModel = bpmnModelService.findBpmnModel(modelId);
		if (bpmnModel == null) {
			logger.error("模型信息未找到");
			throw new FacadeException("s.common.error.param");
		}

		// 资源信息取得
		ResourceInfoModel resourceInfo = resourceInfoService.findResourceInfo(bpmnModel.getResourceId());
		if (resourceInfo == null) {
			logger.error("模型资源未找到");
			throw new FacadeException("s.common.error.param");
		}

		// 发布模型
		String targetFilePath = CommonUtil.convertToAbsolutePath(resourceInfo.getResourcePath());
		FileInputStream in = null;
		try {
			in = new FileInputStream(targetFilePath);
			Deployment deployment =  repositoryService.createDeployment().addInputStream(targetFilePath, in).deploy();
			return deployment.getId();
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

}
