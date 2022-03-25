/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.proc.notice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import we.core.dto.ProcessDto;
import we.core.proc.IProcess;
import we.website.service.NoticeInfoService;

/**
 * 刷新用户通知
 *
 * @author cp0
 * @since 0.0
 */
@Component
public class NoticeRefreshProcess implements IProcess {

	@Autowired
	private NoticeInfoService noticeInfoService;

	/**
	 * 处理实行
	 */
	@Override
	public Object execute(ProcessDto proceeDto, List<Object> resultList) throws Exception {
		// 用户ID
		String userId = proceeDto.getStringData1();
		if (StringUtils.hasText(userId) == false) {
			userId = SecurityContextHolder.getContext().getAuthentication().getName();
		}

		// 发送通知
		noticeInfoService.refresh(userId);
		return null;
	}
}
