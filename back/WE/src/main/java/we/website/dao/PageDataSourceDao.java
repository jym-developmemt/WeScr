/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import java.util.List;

import we.website.model.PageDataSourceModel;

/**
 * 页面数据源信息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface PageDataSourceDao {

	/**
	 * 页面数据源信息取得
	 */
	public PageDataSourceModel findPageDataSource(PageDataSourceModel param);

	/**
	 * 页面数据源信息检索
	 */
	public List<PageDataSourceModel> searchPageDataSource(PageDataSourceModel param);
}
