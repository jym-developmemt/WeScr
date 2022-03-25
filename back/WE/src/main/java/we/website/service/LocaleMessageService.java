/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import java.util.List;

import org.springframework.stereotype.Service;

import we.website.model.LocaleMessageModel;

/**
 * 国际化消息Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public interface LocaleMessageService {

	/**
	 * 国际化消息追加
	 */
	public int insertLocaleMessage(LocaleMessageModel param);

	/**
	 * 国际化消息删除
	 */
	public int deleteLocaleMessage(LocaleMessageModel param);

	/**
	 * 国际化消息检索
	 */
	public List<LocaleMessageModel> searchLocaleMessage(LocaleMessageModel param);

}
