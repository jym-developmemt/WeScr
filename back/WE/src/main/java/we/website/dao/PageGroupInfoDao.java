/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import we.website.model.PageGroupInfoModel;

/**
 * 页面组信息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface PageGroupInfoDao {
	/**
	 * 页面组信息取得
	 */
	public PageGroupInfoModel findPageGroupInfo(String groupId);
}
