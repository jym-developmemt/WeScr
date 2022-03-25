/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.bpmn;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.core.CoreProperties;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.ResourceInfoService;

/**
 * 删除BPMN模型
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class BpmnModelDeleteProcess implements IProcess {

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private ResourceInfoService resourceInfoService;

	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		// 资源ID
		List<Map<String, Object>> resourceList = proceeDto.getListData1();
		if (resourceList == null || resourceList.size() == 0) {
			logger.error("资源ID不能为空");
			throw new FacadeException("s.common.error.param");
		}

		// DB删除
		for (Map<String, Object> resourceData : resourceList) {
			String resourceId = CommonUtil.toString(resourceData.get("resource_id"));
			resourceInfoService.deleteResourceInfo(resourceId);
		}

		// 删除现有文件
		for (Map<String, Object> resourceData : resourceList) {
			String resourceId = CommonUtil.toString(resourceData.get("resource_id"));

			// 资源名称
			String resourceName = resourceId + ".bpmn20.xml";

			// 删除现有文件
			String targetFilePath = CommonUtil.convertToAbsolutePath(websiteProp.getUploadPath() + "\\bpmn\\" + resourceName);
			File targetFile = new File(targetFilePath);
			if (targetFile.exists()) {
				targetFile.delete();
			}
		}

		return null;
	}

}
