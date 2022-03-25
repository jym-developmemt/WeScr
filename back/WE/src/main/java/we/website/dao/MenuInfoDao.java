/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import java.util.List;

import we.website.model.MenuInfoModel;

/**
 * 菜单信息Dao
 *
 * @author cp0
 * @since 2.0
 */
public interface MenuInfoDao {

	/**
	 * 菜单信息取得
	 */
	public List<MenuInfoModel> searchMenuInfoList(MenuInfoModel menuInfo);

}
