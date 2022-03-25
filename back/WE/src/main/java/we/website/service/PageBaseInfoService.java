/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import org.springframework.stereotype.Service;

import we.website.model.PageBaseInfoModel;

/**
 * 页面基本信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public interface PageBaseInfoService {

	/**
	 * 页面基本信息取得
	 */
	public PageBaseInfoModel findPageBaseInfo(PageBaseInfoModel param);

}
