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
import we.website.dao.SiteInfoDao;
import we.website.model.LocaleMessageModel;
import we.website.model.SettingInfoModel;
import we.website.model.SiteInfoModel;
import we.website.model.VersionInfoModel;
import we.website.service.SiteInfoService;

/**
 * 站点信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class SiteInfoServiceImpl extends BaseService implements SiteInfoService {

	// 站点信息Dao
	@Autowired
	private SiteInfoDao siteInfoDao;

	// 多语言消息Dao
	@Autowired
	private LocaleMessageDao localeMessageDao;

	/** 最新版本取得 */
	@Override
	public VersionInfoModel findLastVersion() {
		return siteInfoDao.findLastVersion();
	}

	/** 站点信息取得 */
	@Override
	public SiteInfoModel findSiteInfo(SiteInfoModel siteInfo) throws Exception {
		return siteInfoDao.findSiteInfo(siteInfo);
	}

	/** 系统设定信息检索 */
	public List<SettingInfoModel> searchSettingInfo(String settingId) {
		return siteInfoDao.searchSettingInfo(settingId);
	}

	/** 多语言消息检索 */
	public List<LocaleMessageModel> searchLocaleMessage(String localeId) {
		LocaleMessageModel param = new LocaleMessageModel();
		param.setLocaleId(localeId);
		return localeMessageDao.searchLocaleMessage(param);
	}
}
