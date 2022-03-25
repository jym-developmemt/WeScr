/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import java.util.List;

import org.springframework.stereotype.Service;

import we.website.model.PositionInfoModel;

/**
 * 职位信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public interface PositionInfoService {
	/**
	 * 职位信息取得
	 */
	public PositionInfoModel findPositionInfo(PositionInfoModel positionInfo);

	/**
	 * 职位信息取得
	 */
	public List<PositionInfoModel> selectPositionInfo(PositionInfoModel positionInfo);
}
