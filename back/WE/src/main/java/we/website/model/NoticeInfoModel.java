/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import java.util.Date;

import we.base.base.BaseModel;

/**
 * 通知信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class NoticeInfoModel extends BaseModel {

	/** 通知ID */
	private String noticeId;

	/** 图标类型*/
	private String iconType;

	/** 通知标题 */
	private String noticeTitle;

	/** 通知内容 */
	private String noticeContent;

	/** 通知类型 */
	private String noticeType;

	/** 通知时间 */
	private Date noticeDatetime;

	/** 通知链接 */
	private String noticeLink;

	/** 发件人ID */
	private String sendUserId;

	/** 有效开始时间 */
	private Date validStartTime;

	/** 有效终了时间 */
	private Date validEndTime;

	/** 站内通知 */
	private String siteNotice;

	/** 邮件通知 */
	private String mailNotice;

	/** 通知用户ID */
	private String userId;

	/**
	 * Getting method of noticeId
	 *
	 * @return noticeId
	 */
	public String getNoticeId() {
		return noticeId;
	}

	/**
	 * Setting method of noticeId
	 *
	 * @param noticeId
	 */
	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
	}

	/**
	 * Getting method of iconType
	 *
	 * @return iconType
	 */
	public String getIconType() {
		return iconType;
	}

	/**
	 * Setting method of iconType
	 *
	 * @param iconType
	 */
	public void setIconType(String iconType) {
		this.iconType = iconType;
	}

	/**
	 * Getting method of noticeTitle
	 *
	 * @return noticeTitle
	 */
	public String getNoticeTitle() {
		return noticeTitle;
	}

	/**
	 * Setting method of noticeTitle
	 *
	 * @param noticeTitle
	 */
	public void setNoticeTitle(String noticeTitle) {
		this.noticeTitle = noticeTitle;
	}

	/**
	 * Getting method of noticeContent
	 *
	 * @return noticeContent
	 */
	public String getNoticeContent() {
		return noticeContent;
	}

	/**
	 * Setting method of noticeContent
	 *
	 * @param noticeContent
	 */
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}

	/**
	 * Getting method of noticeType
	 *
	 * @return noticeType
	 */
	public String getNoticeType() {
		return noticeType;
	}

	/**
	 * Setting method of noticeType
	 *
	 * @param noticeType
	 */
	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	/**
	 * Getting method of noticeDatetime
	 *
	 * @return noticeDatetime
	 */
	public Date getNoticeDatetime() {
		return noticeDatetime;
	}

	/**
	 * Setting method of noticeDatetime
	 *
	 * @param noticeDatetime
	 */
	public void setNoticeDatetime(Date noticeDatetime) {
		this.noticeDatetime = noticeDatetime;
	}

	/**
	 * Getting method of noticeLink
	 *
	 * @return noticeLink
	 */
	public String getNoticeLink() {
		return noticeLink;
	}

	/**
	 * Setting method of noticeLink
	 *
	 * @param noticeLink
	 */
	public void setNoticeLink(String noticeLink) {
		this.noticeLink = noticeLink;
	}

	/**
	 * Getting method of sendUserId
	 *
	 * @return sendUserId
	 */
	public String getSendUserId() {
		return sendUserId;
	}

	/**
	 * Setting method of sendUserId
	 *
	 * @param sendUserId
	 */
	public void setSendUserId(String sendUserId) {
		this.sendUserId = sendUserId;
	}

	/**
	 * Getting method of validStartTime
	 *
	 * @return validStartTime
	 */
	public Date getValidStartTime() {
		return validStartTime;
	}

	/**
	 * Setting method of validStartTime
	 *
	 * @param validStartTime
	 */
	public void setValidStartTime(Date validStartTime) {
		this.validStartTime = validStartTime;
	}

	/**
	 * Getting method of validEndTime
	 *
	 * @return validEndTime
	 */
	public Date getValidEndTime() {
		return validEndTime;
	}

	/**
	 * Setting method of validEndTime
	 *
	 * @param validEndTime
	 */
	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

	/**
	 * Getting method of siteNotice
	 *
	 * @return siteNotice
	 */
	public String getSiteNotice() {
		return siteNotice;
	}

	/**
	 * Setting method of siteNotice
	 *
	 * @param siteNotice
	 */
	public void setSiteNotice(String siteNotice) {
		this.siteNotice = siteNotice;
	}

	/**
	 * Getting method of mailNotice
	 *
	 * @return mailNotice
	 */
	public String getMailNotice() {
		return mailNotice;
	}

	/**
	 * Setting method of mailNotice
	 *
	 * @param mailNotice
	 */
	public void setMailNotice(String mailNotice) {
		this.mailNotice = mailNotice;
	}

	/**
	 * Getting method of userId
	 *
	 * @return userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Setting method of userId
	 *
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

}
