/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.util;

import java.util.Date;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import we.core.CoreProperties;
import we.website.model.VersionInfoModel;
import we.website.service.SiteInfoService;

/**
 * 版本管理类
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class VersionHolder {

	// 信息量
	private static Semaphore semaphore = new Semaphore(1);

	// 版本ID
	private static int version = -1;

	// 有效结果时间
	private static Date endDate = null;

	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private SiteInfoService siteInfoService;

	/**
	 * 取得Web服务URL
	 *
	 * @return Web服务URL
	 */
	public int getLastVersion() throws Exception {
		try {
			semaphore.acquire();

			// 判断是否需要刷新版本
			boolean refreshVersion = false;
			if (version == -1) {
				refreshVersion = true;
			} else if (endDate != null && new Date().getTime() > endDate.getTime()) {
				refreshVersion = true;
			}

			if (refreshVersion) {
				// 取得最新版本信息
				VersionInfoModel versionInfo = siteInfoService.findLastVersion();
				version = versionInfo.getVersionId();

				// 下回最长刷新时间
				long nextTime = 0;
				if (websiteProp.getVersionRefreshTime() > 0) {
					nextTime = new Date().getTime() + websiteProp.getVersionRefreshTime() * 60 * 1000;
				}

				// 当前版最终有效时间
				if (versionInfo.getEndDate() != null) {
					if (versionInfo.getEndDate().getTime() < nextTime) {
						nextTime = versionInfo.getEndDate().getTime();
					}
				}

				if (nextTime > 0) {
					endDate = new Date(nextTime);
				}
			}
			return version;

		} finally {
			semaphore.release();
		}

	}
}
