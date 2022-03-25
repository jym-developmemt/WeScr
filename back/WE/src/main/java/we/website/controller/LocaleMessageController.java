/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import we.base.base.BaseController;
import we.base.dto.SendDto;
import we.website.dto.LocaleMessageDto;
import we.website.facade.LocaleMessageFacade;
import we.website.model.LocaleMessageModel;

/**
 * 消息国际化Controller
 *
 * @author cp0
 * @since 0.0
 */
@RestController
@RequestMapping("/localeMessage")
public class LocaleMessageController extends BaseController {

	/** 消息国际化Facade */
	@Autowired
	private LocaleMessageFacade localeMessageFacade;

	/**
	 * 国际化消息取得
	 */
	@RequestMapping("/search")
	public SendDto searchLocaleMessage(@RequestBody LocaleMessageDto localeMessageDto) throws Exception {
		// 国际化信息取得
		Map<String, List<LocaleMessageModel>> messageInfo = localeMessageFacade.searchLocaleMessage(localeMessageDto);
		return successResult(messageInfo);
	}

	/**
	 * 国际化消息保存
	 */
	@RequestMapping("/save")
	public SendDto saveLocaleMessage(@RequestBody LocaleMessageDto localeMessageDto) throws Exception {
		// 国际化信息保存
		int result = localeMessageFacade.saveLocaleMessage(localeMessageDto);
		return successResult(result);
	}
}
