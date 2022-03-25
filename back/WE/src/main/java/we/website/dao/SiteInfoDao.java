/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import java.util.List;

import we.website.model.SettingInfoModel;
import we.website.model.SiteInfoModel;
import we.website.model.VersionInfoModel;

/**
 * 站点信息Dao
 *
 * @author cp0
 * @since 3.0
 */
public interface SiteInfoDao {
	/**
	 * 最新版本取得
	 */
	public VersionInfoModel findLastVersion();

	/**
	 * 站点信息取得
	 */
	public SiteInfoModel findSiteInfo(SiteInfoModel siteInfo);

	/**
	 * 系统设定信息检索
	 */
	public List<SettingInfoModel> searchSettingInfo(String settingId);
}
