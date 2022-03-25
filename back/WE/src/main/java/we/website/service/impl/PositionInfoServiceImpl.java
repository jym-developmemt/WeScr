/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.website.dao.PositionInfoDao;
import we.website.model.PositionInfoModel;
import we.website.service.PositionInfoService;

/**
 * 职位信息Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class PositionInfoServiceImpl extends BaseService implements PositionInfoService {

	@Autowired
	private PositionInfoDao positionInfoDao;

	/**
	 * 职位信息取得
	 */
	public PositionInfoModel findPositionInfo(PositionInfoModel positionInfo) {
		return positionInfoDao.findPositionInfo(positionInfo);
	}

	/**
	 * 职位信息取得
	 */
	public List<PositionInfoModel> selectPositionInfo(PositionInfoModel positionInfo) {
		return positionInfoDao.selectPositionInfo(positionInfo);
	}
}
