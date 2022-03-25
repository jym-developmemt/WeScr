/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import we.base.base.BaseFacade;
import we.base.exception.FacadeException;
import we.base.util.CommonUtil;
import we.website.constant.Constant;
import we.website.dto.UserInfoDto;
import we.website.facade.UserInfoFacade;
import we.website.model.SettingInfoModel;
import we.website.model.UserInfoModel;
import we.website.service.SiteInfoService;
import we.website.service.UserInfoService;

/**
 * 用户信息Facade
 *
 * @author cp0
 * @since 3.0
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoFacadeImpl extends BaseFacade implements UserInfoFacade {

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private SiteInfoService siteInfoService;

	/**
	 * 用户信息取得
	 */
	@Override
	public Map<String, Object> findUserInfo(UserInfoDto userInfoDto) throws FacadeException {
		Map<String, Object> rtnMap = new HashMap<String, Object>();

		UserInfoModel param = new UserInfoModel();
		param.setUserId(userInfoDto.getUserId());
		param.setLoginUserId(userInfoDto.getLoginUserId());
		param.setQywxUserId(userInfoDto.getQywxUserId());
		param.setQywxOpenId(userInfoDto.getQywxOpenId());
		UserInfoModel userInfo = userInfoService.findUserInfo(param);
		if (userInfo == null) {
			logger.error("用户信息未找到");
			throw new FacadeException("s.common.error.none");
		}
		rtnMap.put("userInfo", userInfo);

		List<SettingInfoModel> languagesList = siteInfoService.searchSettingInfo(Constant.SETTING_ID_LANGUAGE);
		SettingInfoModel localeSettingInfo = null;
		for (SettingInfoModel settingInfo : languagesList) {
			if (CommonUtil.equals(userInfo.getLocaleSettingIndex(), settingInfo.getSettingIndex())) {
				localeSettingInfo = settingInfo;
				break;
			}
		}
		if (localeSettingInfo != null) {
			rtnMap.put("locale", new String[] { localeSettingInfo.getVarchar3(), localeSettingInfo.getVarchar4() });
		} else {
			rtnMap.put("locale", new String[2]);
		}

		return rtnMap;
	}

	/**
	 * 用户前回登陆时间更新
	 */
	@Override
	public void updateLoginDate(String userId) {
		userInfoService.updateLoginDate(userId);
	}

	/**
	 * 更新用户信息
	 */
	@Override
	public int updateUserInfo(UserInfoDto userInfoDto) throws Exception {
		// 用户更新
		UserInfoModel userInfo = new UserInfoModel();
		BeanUtils.copyProperties(userInfo, userInfoDto);
		return userInfoService.updateUserInfo(userInfo);
	}
}
