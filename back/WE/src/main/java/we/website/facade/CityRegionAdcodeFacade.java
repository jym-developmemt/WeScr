/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade;

import java.util.List;

import we.website.dto.CityRegionAdcodeDto;
import we.website.model.CityRegionAdcodeModel;

/**
 * 行政区域信息Facade
 *
 * @author cp0
 * @since 0.0
 */
public interface CityRegionAdcodeFacade {

	/**
	 * 行政区域信息取得
	 */
	public List<CityRegionAdcodeModel> searchCityRegionAdcode(CityRegionAdcodeDto cityRegionAdcodeDto) throws Exception;

}
