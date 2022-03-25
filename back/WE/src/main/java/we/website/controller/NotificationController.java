/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import we.base.base.BaseController;
import we.website.facade.NoticeInfoFacade;

/**
 * 通知信息Controller
 *
 * @author cp0
 * @since 0.0
 */
@Controller
public class NotificationController extends BaseController {

	@Autowired
	private NoticeInfoFacade noticeInfoFacade;

	@MessageMapping("/list")
	public void search(String userId) {
		noticeInfoFacade.sendNotice(userId);
	}
}
