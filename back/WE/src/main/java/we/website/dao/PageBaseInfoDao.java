/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import we.website.model.PageBaseInfoModel;

/**
 * 页面基本信息Dao
 *
 * @author cp0
 * @since 3.0
 */
public interface PageBaseInfoDao {

	/**
	 * 页面基本信息取得
	 */
	public PageBaseInfoModel findPageBaseInfo(PageBaseInfoModel param);
}
