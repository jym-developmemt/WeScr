/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.website.dao.PageBaseInfoDao;
import we.website.model.PageBaseInfoModel;
import we.website.service.PageBaseInfoService;

/**
 * 页面基本信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class PageBaseInfoServiceImpl extends BaseService implements PageBaseInfoService {

	// 页面基本信息Dao
	@Autowired
	private PageBaseInfoDao pageBaseInfoDao;

	/**
	 * 页面基本信息取得
	 */
	@Override
	public PageBaseInfoModel findPageBaseInfo(PageBaseInfoModel param) {
		return pageBaseInfoDao.findPageBaseInfo(param);
	}
}
