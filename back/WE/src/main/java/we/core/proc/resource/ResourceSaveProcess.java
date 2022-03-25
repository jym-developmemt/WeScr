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
import we.website.model.ResourceInfoModel;
import we.website.service.ResourceInfoService;

/**
 * 图片流数据
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class ResourceSaveProcess implements IProcess {

	@Autowired
	private ResourceInfoService resourceInfoService;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {

		int result = 0;

		if (StringUtils.hasText(proceeDto.getStringData1())) {
			ResourceInfoModel resourceInfo = new ResourceInfoModel();
			resourceInfo.setResourceId(proceeDto.getStringData1());
			resourceInfo.setTempFlg("1");
			result += resourceInfoService.updateResourceInfo(resourceInfo);
		}

		if (proceeDto.getListData1() != null) {
			for (Map<String, Object> dataMap : proceeDto.getListData1()) {
				ResourceInfoModel resourceInfo = new ResourceInfoModel();
				resourceInfo.setResourceId(CommonUtil.toString(dataMap.get("resource_id")));
				resourceInfo.setTempFlg("1");
				result += resourceInfoService.updateResourceInfo(resourceInfo);
			}
		}

		return result;
	}
}
