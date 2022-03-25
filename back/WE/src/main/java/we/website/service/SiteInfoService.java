/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import java.util.List;

import org.springframework.stereotype.Service;

import we.website.model.LocaleMessageModel;
import we.website.model.SettingInfoModel;
import we.website.model.SiteInfoModel;
import we.website.model.VersionInfoModel;

/**
 * 站点信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public interface SiteInfoService {

	/** 最新版本取得 */
	public VersionInfoModel findLastVersion();

	/** 站点信息取得 */
	public SiteInfoModel findSiteInfo(SiteInfoModel siteInfo) throws Exception;

	/** 系统设定信息检索 */
	public List<SettingInfoModel> searchSettingInfo(String settingId);

	/** 多语言消息检索 */
	public List<LocaleMessageModel> searchLocaleMessage(String localeId);

}
