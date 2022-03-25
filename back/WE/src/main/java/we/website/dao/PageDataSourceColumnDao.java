/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import java.util.List;

import we.website.model.PageDataSourceColumnModel;

/**
 * 数据源列信息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface PageDataSourceColumnDao {

	/**
	 * 数据源列信息检索
	 */
	public List<PageDataSourceColumnModel> searchDataSourceColumn(PageDataSourceColumnModel param);
}
