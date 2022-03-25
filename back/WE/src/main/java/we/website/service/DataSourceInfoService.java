/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import org.springframework.stereotype.Service;

import we.website.model.DataSourceInfoModel;

/**
 * 数据源信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public interface DataSourceInfoService {

	/**
	 * 数据源信息检索
	 */
	public DataSourceInfoModel findDataSource(String dataSourceId);

}
