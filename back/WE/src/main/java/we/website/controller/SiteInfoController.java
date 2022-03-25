/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import we.base.base.BaseController;
import we.base.dto.SendDto;
import we.base.exception.FacadeException;
import we.website.dto.SiteInfoDto;
import we.website.facade.MenuInfoFacade;
import we.website.facade.SiteInfoFacade;
import we.website.model.MenuInfoModel;

/**
 * 站点信息Controller
 *
 * @author cp0
 * @since 0.0
 */
@RestController
@RequestMapping("/siteInfo")
public class SiteInfoController extends BaseController {

	/** 站点信息Facade */
	@Autowired
	private SiteInfoFacade siteInfoFacade;

	/** 菜单信息Facade */
	@Autowired
	private MenuInfoFacade menuInfoFacade;

	/**
	 * 站点信息取得
	 */
	@RequestMapping("/find")
	public SendDto findSiteInfo(@RequestBody SiteInfoDto siteInfoDto) throws Exception {
		// 站点信息取得
		Map<String, Object> siteInfo = siteInfoFacade.findSiteInfo(siteInfoDto);
		if (siteInfo == null) {
			return errorResult("s.common.error.none");
		}
		return successResult(siteInfo);
	}

	/**
	 * 消息数据取得
	 */
	@RequestMapping("/message")
	public SendDto findMessage(@RequestBody SiteInfoDto siteInfoDto) throws Exception {
		// 消息数据取得
		Map<String, String> messageInfo = siteInfoFacade.findMessages(siteInfoDto);
		return successResult(messageInfo);
	}

	/**
	 * 站点菜单取得
	 */
	@RequestMapping("/menu")
	public SendDto findMenuInfo(@RequestBody SiteInfoDto siteInfoDto) throws Exception {
		try {
			// 菜单信息取得
			List<MenuInfoModel> menuInfoList = menuInfoFacade.searchMenuInfo(siteInfoDto);
			return successResult(menuInfoList);
		} catch (FacadeException e) {
			return errorResult(e.getMessage());
		} catch (Exception e) {
			logger.error("菜单信息取得失败", e);
			return errorResult("s.common.error.process");
		}
	}
}
