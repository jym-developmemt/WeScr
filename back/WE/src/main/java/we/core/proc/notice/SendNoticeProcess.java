/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.NoticeInfoService;

/**
 * 手动发送信息
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class SendNoticeProcess implements IProcess {

	@Autowired
	private NoticeInfoService noticeInfoService;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 发送通知
		noticeInfoService.sendNotice();
		return null;
	}
}
