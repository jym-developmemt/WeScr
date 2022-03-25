/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.base.util.TokenUtils;
import we.website.dao.NoticeInfoDao;
import we.website.model.NoticeInfoModel;
import we.website.service.NoticeInfoService;

/**
 * 通知信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class NoticeInfoServiceImpl extends BaseService implements NoticeInfoService {

	@Autowired
	private SimpMessageSendingOperations messageTemplate;

	// 通知信息Dao
	@Autowired
	private NoticeInfoDao noticeInfoDao;

	/**
	 * 用户登录通知
	 */
	public void sendNotice(String userId) {
		// 发送对象
		String destination = "/topic/" + userId;

		// 用户通知件数取得
		int count = noticeInfoDao.countUserNotice(userId);

		// 用户登陆通知一览取得
		NoticeInfoModel param = new NoticeInfoModel();
		param.setSiteNotice("3");
		param.setUserId(userId);
		List<NoticeInfoModel> noticeList = noticeInfoDao.searchNewNotice(param);
		if (noticeList.size() > 0) {
			// 发送登陆通知
			for (NoticeInfoModel noticeInfo : noticeList) {
				Map<String, Object> sendData = new HashMap<String, Object>();
				sendData.put("noticeCnt", count);
				sendData.put("notice", noticeInfo);
				messageTemplate.convertAndSend(destination, sendData);
			}
		} else {
			// 更新通知件数
			Map<String, Object> sendData = new HashMap<String, Object>();
			sendData.put("noticeCnt", count);
			messageTemplate.convertAndSend(destination, sendData);
		}
	}

	/**
	 * 用户未读信息更新
	 */
	@Override
	public void refresh(String userId) {
		// 发送对象
		String destination = "/topic/" + userId;

		// 用户通知件数取得
		int count = noticeInfoDao.countUserNotice(userId);

		// 更新通知件数
		Map<String, Object> sendData = new HashMap<String, Object>();
		sendData.put("noticeCnt", count);
		messageTemplate.convertAndSend(destination, sendData);
	}

	/**
	 * 发送站内通知
	 */
	@Scheduled(cron = "1 * * * * ?")
	public void sendNotice() {
		// 用户认证
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			SecurityContextHolder.getContext().setAuthentication(TokenUtils.createBatchAuthentication());
		}

		// 未发送通知一览取得
		NoticeInfoModel param = new NoticeInfoModel();
		param.setSiteNotice("0");
		List<NoticeInfoModel> noticeList = noticeInfoDao.searchNewNotice(param);
		if (noticeList.size() > 0) {
			List<String> noticeIdList = new ArrayList<String>();

			for (NoticeInfoModel noticeInfo : noticeList) {
				// 发送对象
				String userId = noticeInfo.getUserId();
				String destination = "/topic/" + noticeInfo.getUserId();

				// 标记为已发送
				if (noticeIdList.contains(noticeInfo.getNoticeId()) == false) {
					noticeIdList.add(noticeInfo.getNoticeId());
					noticeInfoDao.updateNotice(noticeInfo);
				}

				// 通知件数更新
				int count = noticeInfoDao.countUserNotice(userId);

				// 发送通知
				Map<String, Object> sendData = new HashMap<String, Object>();
				sendData.put("noticeCnt", count);
				sendData.put("notice", noticeInfo);
				messageTemplate.convertAndSend(destination, sendData);
			}
		}
	}
}
