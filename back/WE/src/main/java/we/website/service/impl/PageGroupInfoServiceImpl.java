/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.website.dao.PageGroupInfoDao;
import we.website.model.PageGroupInfoModel;
import we.website.service.PageGroupInfoService;

/**
 * 页面组信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class PageGroupInfoServiceImpl extends BaseService implements PageGroupInfoService {

	// 页面组信息Dao
	@Autowired
	private PageGroupInfoDao pageGroupInfoDao;

	/**
	 * 页面组信息取得
	 */
	@Override
	public PageGroupInfoModel findPageGroupInfo(String groupId) {
		return pageGroupInfoDao.findPageGroupInfo(groupId);
	}
}
