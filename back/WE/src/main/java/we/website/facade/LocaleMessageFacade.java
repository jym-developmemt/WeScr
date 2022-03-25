/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade;

import java.util.List;
import java.util.Map;

import we.website.dto.LocaleMessageDto;
import we.website.model.LocaleMessageModel;

/**
 * 消息国际化Facade
 *
 * @author cp0
 * @since 0.0
 */
public interface LocaleMessageFacade {

	/** 消息国际化取得 */
	public Map<String, List<LocaleMessageModel>> searchLocaleMessage(LocaleMessageDto localeMessageDto) throws Exception;


	/** 消息国际化保存 */
	public int saveLocaleMessage(LocaleMessageDto localeMessageDto) throws Exception;

}
