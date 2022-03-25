/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import we.base.base.BaseController;
import we.base.dto.SendDto;
import we.base.exception.FacadeException;
import we.website.dto.CityRegionAdcodeDto;
import we.website.facade.CityRegionAdcodeFacade;
import we.website.model.CityRegionAdcodeModel;

/**
 * 行政区划信息Controller
 *
 * @author cp0
 * @since 0.0
 */
@RestController
@RequestMapping("/adcode")
public class CityRegionAdcodeController extends BaseController {

	@Autowired
	private CityRegionAdcodeFacade cityRegionAdcodeFacade;

	/**
	 * 行政区域信息取得
	 */
	@RequestMapping("/list")
	public SendDto searchCityRegionAdcode(@RequestBody CityRegionAdcodeDto cityRegionAdcodeDto) throws Exception {
		try {
			// 页面信息取得
			List<CityRegionAdcodeModel> cityRegionAdcodeList = cityRegionAdcodeFacade.searchCityRegionAdcode(cityRegionAdcodeDto);
			return successResult(cityRegionAdcodeList);
		} catch (FacadeException e) {
			return errorResult(e.getMessage());
		} catch (Exception e) {
			logger.error("行政区域信息取得失败", e);
			return errorResult("s.common.error.process");
		}
	}
}
