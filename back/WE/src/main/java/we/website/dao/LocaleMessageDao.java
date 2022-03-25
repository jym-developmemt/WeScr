/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import java.util.List;

import we.website.model.LocaleMessageModel;

/**
 * 多语言消息Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface LocaleMessageDao {
	/**
	 * 多语言消息检索
	 */
	public List<LocaleMessageModel> searchLocaleMessage(LocaleMessageModel localeMessage);

	/**
	 * 多语言消息追加
	 */
	public int insertLocaleMessage(LocaleMessageModel localeMessage);

	/**
	 * 多语言消息删除
	 */
	public int deleteLocaleMessage(LocaleMessageModel localeMessage);
}
