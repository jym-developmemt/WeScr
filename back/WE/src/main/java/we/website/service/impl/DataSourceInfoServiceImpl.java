/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.website.dao.DataSourceInfoDao;
import we.website.model.DataSourceInfoModel;
import we.website.service.DataSourceInfoService;

/**
 * 数据源信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class DataSourceInfoServiceImpl extends BaseService implements DataSourceInfoService {

	// 数据源信息Dao
	@Autowired
	private DataSourceInfoDao dataSourceInfoDao;

	/**
	 * 数据源信息检索
	 */
	@Override
	public DataSourceInfoModel findDataSource(String dataSourceId) {
		return dataSourceInfoDao.findDataSource(dataSourceId);
	}
}
