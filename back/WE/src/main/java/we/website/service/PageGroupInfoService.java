/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import org.springframework.stereotype.Service;

import we.website.model.PageGroupInfoModel;

/**
 * 页面组信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public interface PageGroupInfoService {
	/**
	 * 页面组信息取得
	 */
	public PageGroupInfoModel findPageGroupInfo(String groupId);

}
