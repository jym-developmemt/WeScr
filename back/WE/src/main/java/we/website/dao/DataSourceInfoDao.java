/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import we.website.model.DataSourceInfoModel;

/**
 * 数据源信息Dao
 *
 * @author cp0
 * @since 3.0
 */
public interface DataSourceInfoDao {

	/**
	 * 数据源信息检索
	 */
	public DataSourceInfoModel findDataSource(String dataSourceId);
}
