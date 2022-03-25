/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import org.springframework.stereotype.Service;

/**
 * 通知信息取得Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public interface NoticeInfoService {

	/**
	 * 用户登录通知
	 */
	public void sendNotice(String userId);

	/**
	 * 用户未读信息更新
	 */
	public void refresh(String userId);

	/**
	 * 发送通知
	 */
	public void sendNotice();
}
