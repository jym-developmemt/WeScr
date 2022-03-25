/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import java.util.List;

import org.springframework.stereotype.Service;

import we.website.model.PageDataSourceColumnModel;
import we.website.model.PageDataSourceModel;

/**
 * 页面数据源信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public interface PageDataSourceService {

	/**
	 * 页面数据源信息取得
	 */
	public PageDataSourceModel findPageDataSource(PageDataSourceModel param);

	/**
	 * 页面数据源信息检索
	 */
	public List<PageDataSourceModel> searchPageDataSource(PageDataSourceModel param);

	/**
	 * 页面数据源信息检索
	 */
	public List<PageDataSourceColumnModel> searchPageDataSourceColumn(PageDataSourceColumnModel param);
}
