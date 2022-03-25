/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseFacade;
import we.website.facade.NoticeInfoFacade;
import we.website.service.NoticeInfoService;

/**
 * 通知信息Facade
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class NoticeInfoFacadeImpl extends BaseFacade implements NoticeInfoFacade {

	@Autowired
	private NoticeInfoService noticeInfoService;

	/**
	 * 用户登录通知
	 */
	@Override
	public void sendNotice(String userId) {
		noticeInfoService.sendNotice(userId);
	}
}
