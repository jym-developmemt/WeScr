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
import we.website.dao.MenuInfoDao;
import we.website.model.MenuInfoModel;
import we.website.service.MenuInfoService;

/**
 * 菜单信息取得Service
 *
 * @author cp0
 * @since 2.0
 */
@Service
public class MenuInfoServiceImpl extends BaseService implements MenuInfoService {

	// 站点信息Dao
	@Autowired
	private MenuInfoDao menuInfoDao;

	/**
	 * 菜单信息取得
	 */
	public List<MenuInfoModel> searchMenuInfoList(String parentMenuId) throws Exception {
		MenuInfoModel param = new MenuInfoModel();
		param.setParentId(parentMenuId);
		return menuInfoDao.searchMenuInfoList(param);
	}
}
