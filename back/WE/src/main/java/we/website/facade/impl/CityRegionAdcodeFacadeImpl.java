/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseFacade;
import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.website.dto.CityRegionAdcodeDto;
import we.website.facade.CityRegionAdcodeFacade;
import we.website.model.CityRegionAdcodeModel;
import we.website.service.CityRegionAdcodeService;

/**
 * 行政区域信息Facade
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class CityRegionAdcodeFacadeImpl extends BaseFacade implements CityRegionAdcodeFacade {

	@Autowired
	private CityRegionAdcodeService cityRegionAdcodeService;

	/**
	 * 行政区域信息取得
	 */
	@Override
	public List<CityRegionAdcodeModel> searchCityRegionAdcode(CityRegionAdcodeDto cityRegionAdcodeDto) throws Exception {
		// 查找等级
		String level = cityRegionAdcodeDto.getLevel();

		if (CommonUtil.equals(level, "0")) {
			// 省
			return cityRegionAdcodeService.searchProv();
		} else if (CommonUtil.equals(level, "1")) {
			// 市
			return cityRegionAdcodeService.searchCity(cityRegionAdcodeDto.getParentCode());
		} else if (CommonUtil.equals(level, "2")) {
			// 区
			return cityRegionAdcodeService.searchCoun(cityRegionAdcodeDto.getParentCode());
		} else if (CommonUtil.equals(level, "3")) {
			// 县
			return cityRegionAdcodeService.searchTown(cityRegionAdcodeDto.getParentCode());
		}

		logger.error("检索参数不正");
		throw new FacadeException("s.common.error.param");
	}
}
