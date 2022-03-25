/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import we.base.base.BaseController;
import we.base.dto.SendDto;
import we.base.exception.FacadeException;
import we.website.dto.ResourceInfoDto;
import we.website.facade.ResourceInfoOthersFacade;

/**
 * 资源信息Controller
 *
 * @author cp0
 * @since 0.0
 */
@RestController
@RequestMapping("/resourceOther")
public class ResourceInfoOthersController extends BaseController {

	@Autowired
	private ResourceInfoOthersFacade resourceInfoOthersFacade;

	/**
	 * 资源上传
	 * Content-Type: multipart/form-data
	 */
	@RequestMapping("/upload")
	public SendDto upload(ResourceInfoDto resourceInfoDto) throws Exception {
		try {
			// 文件上传
			String resourceId = resourceInfoOthersFacade.upload(resourceInfoDto);
			return successResult(resourceId);
		} catch (FacadeException e) {
			return errorResult(e.getMessage());
		} catch (Exception e) {
			logger.error("资源上传失败", e);
			return errorResult("s.common.error.process");
		}
	}

}
