/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.website.dao.CityRegionAdcodeDao;
import we.website.model.CityRegionAdcodeModel;
import we.website.service.CityRegionAdcodeService;

/**
 * 行政区域信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class CityRegionAdcodeServiceImpl extends BaseService implements CityRegionAdcodeService {

	// 行政区域信息Dao
	@Autowired
	private CityRegionAdcodeDao cityRegionAdcodeDao;

	/**
	 * 省信息取得
	 */
	public List<CityRegionAdcodeModel> searchProv() {
		return cityRegionAdcodeDao.searchProv();
	}

	/**
	 * 市信息取得
	 */
	public List<CityRegionAdcodeModel> searchCity(String codeProv) {
		return cityRegionAdcodeDao.searchCity(codeProv);
	}

	/**
	 * 区信息取得
	 */
	public List<CityRegionAdcodeModel> searchCoun(String codeCity) {
		return cityRegionAdcodeDao.searchCoun(codeCity);
	}

	/**
	 * 县信息取得
	 */
	public List<CityRegionAdcodeModel> searchTown(String codeCoun) {
		return cityRegionAdcodeDao.searchTown(codeCoun);
	}
}
