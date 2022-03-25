/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import we.base.base.BaseController;
import we.base.dto.SendDto;
import we.website.dto.UserInfoDto;
import we.website.facade.UserInfoFacade;

/**
 * 用户信息Controller
 *
 * @author cp0
 * @since 0.0
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController extends BaseController {

	/** 用户信息Facade */
	@Autowired
	private UserInfoFacade userInfoFacade;

	/**
	 * 站点信息取得
	 */
	@RequestMapping("/find")
	public SendDto findUserInfo() throws Exception {

		// 当前登录者信息
		UserInfoDto userInfoDto = new UserInfoDto();
		userInfoDto.setUserId(SecurityContextHolder.getContext().getAuthentication().getName());

		// 站点信息取得
		Map<String,Object> loginInfo = userInfoFacade.findUserInfo(userInfoDto);
		return successResult(loginInfo);
	}
}
