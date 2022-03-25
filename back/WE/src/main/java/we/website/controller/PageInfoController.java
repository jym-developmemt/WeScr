/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import we.base.base.BaseController;
import we.base.dto.SendDto;
import we.base.exception.FacadeException;
import we.website.dto.PageInfoDto;
import we.website.facade.PageInfoFacade;
import we.website.model.PageGroupInfoModel;

/**
 * 页面信息Controller
 *
 * @author cp0
 * @since 0.0
 */
@RestController
@RequestMapping("/pageInfo")
public class PageInfoController extends BaseController {

	@Autowired
	private PageInfoFacade pageInfoFacade;

	/**
	 * 页面组信息取得
	 */
	@RequestMapping("/group")
	public SendDto findGroupInfo(@RequestBody PageInfoDto pageInfoDto) throws Exception {
		try {
			// 页面组信息取得
			PageGroupInfoModel pageGroupInfo = pageInfoFacade.findPageGroupInfo(pageInfoDto);
			return successResult(pageGroupInfo);
		} catch (FacadeException e) {
			return errorResult(e.getMessage());
		} catch (Exception e) {
			logger.error("页面组信息取得失败", e);
			return errorResult("s.common.error.process");
		}
	}

	/**
	 * 页面信息取得
	 */
	@RequestMapping("/find")
	@ResponseBody
	public SendDto findPageInfo(PageInfoDto pageInfoDto) throws Exception {
		try {
			// 页面信息取得
			Map<String, Object> pageInfoData = pageInfoFacade.findPageInfo(pageInfoDto);
			return successResult(pageInfoData);
		} catch (FacadeException e) {
			return errorResult(e.getMessage());
		} catch (Exception e) {
			logger.error("页面信息取得失败", e);
			return errorResult("s.common.error.process");
		}
	}
}
