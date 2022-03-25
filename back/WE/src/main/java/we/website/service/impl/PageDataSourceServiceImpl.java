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
import we.website.dao.PageDataSourceColumnDao;
import we.website.dao.PageDataSourceDao;
import we.website.model.PageDataSourceColumnModel;
import we.website.model.PageDataSourceModel;
import we.website.service.PageDataSourceService;

/**
 * 页面数据源信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class PageDataSourceServiceImpl extends BaseService implements PageDataSourceService {

	// 页面数据源信息Dao
	@Autowired
	private PageDataSourceDao pageDataSourceDao;

	// 页面数据源列信息Dao
	@Autowired
	private PageDataSourceColumnDao pageDataSourceColumnDao;

	/**
	 * 页面数据源信息取得
	 */
	public PageDataSourceModel findPageDataSource(PageDataSourceModel param) {
		return pageDataSourceDao.findPageDataSource(param);
	}

	/**
	 * 页面数据源信息检索
	 */
	@Override
	public List<PageDataSourceModel> searchPageDataSource(PageDataSourceModel param) {
		return pageDataSourceDao.searchPageDataSource(param);
	}

	/**
	 * 页面数据源信息检索
	 */
	@Override
	public List<PageDataSourceColumnModel> searchPageDataSourceColumn(PageDataSourceColumnModel param) {
		return pageDataSourceColumnDao.searchDataSourceColumn(param);
	}
}
