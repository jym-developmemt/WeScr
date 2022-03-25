/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dto;

import java.util.List;

import we.base.dto.ReceiveDto;
import we.website.model.LocaleMessageModel;

/**
 * 消息国际化Dto
 *
 * @author cp0
 * @since 0.0
 */
public class LocaleMessageDto extends ReceiveDto {

	/** 言语ID */
	private String localeId;

	/** 消息来源ID */
	private String sourceId;

	/** 消息一览 */
	private List<LocaleMessageModel> dataList;

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

	/**
	 * Getting method of dataList
	 *
	 * @return dataList
	 */
	public List<LocaleMessageModel> getDataList() {
		return dataList;
	}

	/**
	 * Setting method of dataList
	 *
	 * @param dataList
	 */
	public void setDataList(List<LocaleMessageModel> dataList) {
		this.dataList = dataList;
	}

}
