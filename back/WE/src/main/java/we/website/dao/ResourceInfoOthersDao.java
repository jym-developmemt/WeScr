/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import java.util.List;

import we.website.model.ResourceInfoModel;

/**
 * 资源信息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface ResourceInfoOthersDao {
	
	/**
	 * 资源信息追加
	 */
	public int addResourceInfo(ResourceInfoModel resourceInfo);

	/**
	 * 资源信息更新
	 */
	public int updateResourceInfo(ResourceInfoModel resourceInfo);

	/**
	 * 资源信息删除
	 */
	public int deleteResourceInfo(String resourceId);
	
	/**
	 * 取得首页过期的临时资源
	 */
	public List<ResourceInfoModel> searchHomePageExpiredResource();
}
