/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import we.base.base.BaseModel;

/**
 * 消息模型
 *
 * @author cp0
 * @since 0.0
 */
public class LocaleMessageModel extends BaseModel {

	/** 言语ID */
	private String localeId;

	/** 消息来源类型 */
	private String messageType;

	/** 消息ID */
	private String messageId;

	/** 消息内容 */
	private String messageContent;

	/** 消息来源ID */
	private String sourceId;

	/**
	 * Getting method of localeId
	 *
	 * @return localeId
	 */
	public String getLocaleId() {
		return localeId;
	}

	/**
	 * Setting method of localeId
	 *
	 * @param localeId
	 */
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	/**
	 * Getting method of messageType
	 *
	 * @return messageType
	 */
	public String getMessageType() {
		return messageType;
	}

	/**
	 * Setting method of messageType
	 *
	 * @param messageType
	 */
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	/**
	 * Getting method of messageId
	 *
	 * @return messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * Setting method of messageId
	 *
	 * @param messageId
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * Getting method of messageContent
	 *
	 * @return messageContent
	 */
	public String getMessageContent() {
		return messageContent;
	}

	/**
	 * Setting method of messageContent
	 *
	 * @param messageContent
	 */
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	/**
	 * Getting method of sourceId
	 *
	 * @return sourceId
	 */
	public String getSourceId() {
		return sourceId;
	}

	/**
	 * Setting method of sourceId
	 *
	 * @param sourceId
	 */
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

}
