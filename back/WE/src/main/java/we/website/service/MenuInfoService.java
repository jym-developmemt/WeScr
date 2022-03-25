/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import java.util.List;

import org.springframework.stereotype.Service;

import we.website.model.MenuInfoModel;

/**
 * 菜单信息取得Service
 *
 * @author cp0
 * @since 2.0
 */
@Service
public interface MenuInfoService {

	/** 菜单信息取得 */
	public List<MenuInfoModel> searchMenuInfoList(String parentId) throws Exception;

}
