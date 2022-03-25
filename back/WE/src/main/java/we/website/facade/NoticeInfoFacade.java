/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade;

/**
 * 通知信息Facade
 *
 * @author cp0
 * @since 0.0
 */
public interface NoticeInfoFacade {

	/**
	 * 用户登录通知
	 */
	public void sendNotice(String userId);
}
