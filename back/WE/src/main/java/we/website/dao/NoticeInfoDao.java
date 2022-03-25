/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import java.util.List;

import we.website.model.NoticeInfoModel;

/**
 * 通知信息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface NoticeInfoDao {
	/**
	 * 用户未读信息件数取得
	 */
	public int countUserNotice(String userId);

	/**
	 * 未发送信息取得
	 */
	public List<NoticeInfoModel> searchNewNotice(NoticeInfoModel param);

	/**
	 * 发送信息更新
	 */
	public int updateNotice(NoticeInfoModel noticeInfo);
}
