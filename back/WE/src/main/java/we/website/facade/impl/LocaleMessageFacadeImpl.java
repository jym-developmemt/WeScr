/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseFacade;
import we.base.exception.FacadeException;
import we.website.dto.LocaleMessageDto;
import we.website.facade.LocaleMessageFacade;
import we.website.model.LocaleMessageModel;
import we.website.service.LocaleMessageService;

/**
 * 国际化消息Facade
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class LocaleMessageFacadeImpl extends BaseFacade implements LocaleMessageFacade {

	@Autowired
	private LocaleMessageService localeMessageService;

	/** 消息国际化取得 */
	public Map<String, List<LocaleMessageModel>> searchLocaleMessage(LocaleMessageDto localeMessageDto) throws Exception {

		// 检索参数
		LocaleMessageModel param = new LocaleMessageModel();
		param.setSourceId(localeMessageDto.getSourceId());

		List<LocaleMessageModel> dataList = localeMessageService.searchLocaleMessage(param);

		Map<String, List<LocaleMessageModel>> rtnMap = new HashMap<String, List<LocaleMessageModel>>();

		for (LocaleMessageModel message : dataList) {

			List<LocaleMessageModel> messageList;

			String localeId = message.getLocaleId();
			if (rtnMap.containsKey(localeId)) {
				messageList = rtnMap.get(localeId);
			} else {
				messageList = new ArrayList<LocaleMessageModel>();
				rtnMap.put(localeId, messageList);
			}

			messageList.add(message);
		}

		return rtnMap;
	}


	/** 消息国际化保存 */
	public int saveLocaleMessage(LocaleMessageDto localeMessageDto) throws Exception {
		try {

			// 删除原有消息
			LocaleMessageModel param = new LocaleMessageModel();
			param.setLocaleId(localeMessageDto.getLocaleId());
			param.setSourceId(localeMessageDto.getSourceId());
			localeMessageService.deleteLocaleMessage(param);

			// 追加新信息
			for (LocaleMessageModel data : localeMessageDto.getDataList()) {
				data.setLocaleId(localeMessageDto.getLocaleId());
				data.setSourceId(localeMessageDto.getSourceId());
				localeMessageService.insertLocaleMessage(data);
			}
		} catch (Exception e) {
			logger.error("消息国际化保存失败", e);
			throw new FacadeException("common.error.process");
		}

		return 1;
	}
}
