/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.base.util.CommonUtil;
import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.ResourceInfoService;

/**
 * 资源删除
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class ResourceDeleteProcess implements IProcess {

	@Autowired
	private ResourceInfoService resourceInfoService;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 流程Key
		String resourceId = proceeDto.getStringData1();
		// 文件删除
		if (StringUtils.hasText(resourceId)) {
			return resourceInfoService.deleteResourceInfo(resourceId);
		}

		// 文件删除
		List<Map<String, Object>> resourceList = proceeDto.getListData1();
		if (resourceList != null) {
			int result = 0;
			for (Map<String, Object> resourceInfo : resourceList) {
				result += resourceInfoService.deleteResourceInfo(CommonUtil.toString(resourceInfo.get("resource_id")));
			}
			return result;
		}

		return 0;
	}
}
