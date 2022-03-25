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
import we.website.dao.LocaleMessageDao;
import we.website.model.LocaleMessageModel;
import we.website.service.LocaleMessageService;

/**
 * 数据源信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class LocaleMessageServiceImpl extends BaseService implements LocaleMessageService {

	// 数据源信息Dao
	@Autowired
	private LocaleMessageDao localeMessageDao;
	/**
	 * 国际化消息追加
	 */
	public int insertLocaleMessage(LocaleMessageModel param) {
		return localeMessageDao.insertLocaleMessage(param);
	}

	/**
	 * 国际化消息删除
	 */
	public int deleteLocaleMessage(LocaleMessageModel param) {
		return localeMessageDao.deleteLocaleMessage(param);
	}

	/**
	 * 国际化消息检索
	 */
	public List<LocaleMessageModel> searchLocaleMessage(LocaleMessageModel param) {
		return localeMessageDao.searchLocaleMessage(param);
	}
}
