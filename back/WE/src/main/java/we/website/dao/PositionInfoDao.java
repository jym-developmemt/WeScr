/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import java.util.List;

import we.website.model.PositionInfoModel;

/**
 * 职位信息Dao
 *
 * @author cp0
 * @since 3.0
 */
public interface PositionInfoDao {
	/**
	 * 职位信息取得
	 */
	public PositionInfoModel findPositionInfo(PositionInfoModel positionInfo);

	/**
	 * 职位信息取得
	 */
	public List<PositionInfoModel> selectPositionInfo(PositionInfoModel positionInfo);
}
