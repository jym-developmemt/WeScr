/********************************************************************************
 * Copyright (c) 2017 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.core.dao.SearchDataDao;
import we.core.model.SearchDataModel;
import we.core.service.SearchDataService;

/**
 * 简单数据查询Service实现类
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class SearchDataServiceImpl extends BaseService implements SearchDataService {

	@Autowired
	private SearchDataDao searchDataDao;

	/**
	 * 取得查询数据信息
	 */
	@Override
	public Map<String, Object> getSearchData(SearchDataModel searchDataModel) {
		// 检索实行
		return searchDataDao.getSearchData(searchDataModel);
	}

	/**
	 * 查询数据检索
	 */
	@Override
	public List<Map<String, Object>> getSearchDataList(SearchDataModel searchDataModel) {
		// 检索实行
		return searchDataDao.getSearchDataList(searchDataModel);
	}

}
