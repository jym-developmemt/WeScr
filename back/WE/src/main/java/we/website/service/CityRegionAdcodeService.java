/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import java.util.List;

import we.website.model.CityRegionAdcodeModel;

/**
 * 行政区域信息Service
 *
 * @author cp0
 * @since 0.0
 */
public interface CityRegionAdcodeService {
	/**
	 * 省信息取得
	 */
	public List<CityRegionAdcodeModel> searchProv();

	/**
	 * 市信息取得
	 */
	public List<CityRegionAdcodeModel> searchCity(String codeProv);

	/**
	 * 区信息取得
	 */
	public List<CityRegionAdcodeModel> searchCoun(String codeCity);

	/**
	 * 县信息取得
	 */
	public List<CityRegionAdcodeModel> searchTown(String codeCoun);
}
