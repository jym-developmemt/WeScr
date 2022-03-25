/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import we.base.base.BaseFacade;
import we.base.util.CommonUtil;
import we.base.util.EncryptorUtil;
import we.base.util.VersionHolder;
import we.core.CoreProperties;
import we.website.constant.Constant;
import we.website.dto.SiteInfoDto;
import we.website.facade.SiteInfoFacade;
import we.website.model.LocaleMessageModel;
import we.website.model.SettingInfoModel;
import we.website.model.SiteInfoModel;
import we.website.service.SiteInfoService;

/**
 * 站点信息Facade
 *
 * @author cp0
 * @since 3.0
 */
@Service
public class SiteInfoFacadeImpl extends BaseFacade implements SiteInfoFacade {

	@Autowired
	private CoreProperties websiteProp;

	@Autowired
	private SiteInfoService siteInfoService;

	@Autowired
	private VersionHolder versionHolder;

	/**
	 * 站点信息取得
	 */
	@Override
	public Map<String, Object> findSiteInfo(SiteInfoDto processInfo) throws Exception {

		// 站点信息
		SiteInfoModel siteInfo = generateSiteInfo(processInfo);
		if (siteInfo == null) {
			return null;
		}

		Map<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("siteInfo", siteInfo);

		// 取得多语言信息
		List<SettingInfoModel> languagesList = generateLanguageList();
		rtnMap.put("languages", languagesList);

		if (StringUtils.isEmpty(processInfo.getLocaleId())) {
			SettingInfoModel localeSettingInfo = null;
			for (SettingInfoModel settingInfo : languagesList) {
				if (CommonUtil.equals(siteInfo.getLocaleSettingIndex(), settingInfo.getSettingIndex())) {
					localeSettingInfo = settingInfo;
					break;
				}
			}
			processInfo.setLocaleId(localeSettingInfo.getVarchar3());
			processInfo.setSubLocaleId(localeSettingInfo.getVarchar4());
		}

		rtnMap.put("locale1", processInfo.getLocaleId());
		rtnMap.put("locale2", processInfo.getSubLocaleId());

		// 取得主消息
		Map<String, String> message1 = generateMessageList(processInfo.getLocaleId());
		rtnMap.put("msg1", message1);

		// 取得子消息
		if (StringUtils.hasText(processInfo.getSubLocaleId())) {
			Map<String, String> message2 = generateMessageList(processInfo.getSubLocaleId());
			rtnMap.put("msg2", message2);
		}

		// 第三方应用信息
		rtnMap.put("external", generateExternalInfo());

		return rtnMap;
	}

	/**
	 * 消息数据取得
	 */
	@Override
	public Map<String, String> findMessages(SiteInfoDto processInfo) throws Exception {
		// 取得主消息
		return generateMessageList(processInfo.getLocaleId());
	}

	private SiteInfoModel generateSiteInfo(SiteInfoDto processInfo) throws Exception {
		// 默认站点ID
		String siteId = processInfo.getSiteId();
		if (StringUtils.hasText(siteId) == false) {
			siteId = websiteProp.getDefaultSiteId();
		}

		SiteInfoModel siteInfo = new SiteInfoModel();
		siteInfo.setVersionId(versionHolder.getLastVersion());
		siteInfo.setSiteId(siteId);

		// 站点信息
		siteInfo = siteInfoService.findSiteInfo(siteInfo);
		if (siteInfo == null) {
			return null;
		}

		// 默认站点URL
		String[] defaultPageInfo = siteInfo.getDefaultPageid().replaceAll("/", " ").trim().split(" ");
		StringBuffer strPageInfo = new StringBuffer();
		strPageInfo.append("/");
		strPageInfo.append(EncryptorUtil.encryptString(defaultPageInfo[0], websiteProp.getDesSalt()));
		strPageInfo.append("/");
		strPageInfo.append(EncryptorUtil.encryptString(defaultPageInfo[1], websiteProp.getDesSalt()));
		siteInfo.setDefaultPageid(strPageInfo.toString());

		// 手机首页URL
		if (siteInfo.getMobilePageid() != null) {
			String[] messagePageInfo = siteInfo.getMobilePageid().replaceAll("/", " ").trim().split(" ");
			StringBuffer strMessageInfo = new StringBuffer();
			strMessageInfo.append("/");
			strMessageInfo.append(EncryptorUtil.encryptString(messagePageInfo[0], websiteProp.getDesSalt()));
			strMessageInfo.append("/");
			strMessageInfo.append(EncryptorUtil.encryptString(messagePageInfo[1], websiteProp.getDesSalt()));
			siteInfo.setMobilePageid(strMessageInfo.toString());
		}

		// 消息URL
		if (siteInfo.getMessagePageid() != null) {
			String[] messagePageInfo = siteInfo.getMessagePageid().replaceAll("/", " ").trim().split(" ");
			StringBuffer strMessageInfo = new StringBuffer();
			strMessageInfo.append("/");
			strMessageInfo.append(EncryptorUtil.encryptString(messagePageInfo[0], websiteProp.getDesSalt()));
			strMessageInfo.append("/");
			strMessageInfo.append(EncryptorUtil.encryptString(messagePageInfo[1], websiteProp.getDesSalt()));
			siteInfo.setMessagePageid(strMessageInfo.toString());
		}

		return siteInfo;
	}

	private List<SettingInfoModel> generateLanguageList() {
		// 取得多语言信息
		return siteInfoService.searchSettingInfo(Constant.SETTING_ID_LANGUAGE);
	}

	private Map<String, String> generateMessageList(String localeId) {
		List<LocaleMessageModel> localeMessageList = siteInfoService.searchLocaleMessage(localeId);

		Map<String, String> rtnMap = new HashMap<String, String>();
		for (LocaleMessageModel localeMessage : localeMessageList) {
			rtnMap.put(localeMessage.getMessageId(), localeMessage.getMessageContent());
		}

		return rtnMap;
	}

	private Map<String, Object> generateExternalInfo() {
		Map<String, Object> rtnMap = new HashMap<String, Object>();
		// 用户名密码认证
		rtnMap.put("clientIdSite", websiteProp.getClientIdSite());
		rtnMap.put("clientSecretSite", websiteProp.getClientSecretSite());

		// 企业微信公开信息设定
		rtnMap.put("qyapiCorpid", websiteProp.getQyapiCorpid());
		rtnMap.put("qyapiAgentid", websiteProp.getQyapiAgentid());
		rtnMap.put("qywxClientSecret", websiteProp.getClientSecretQywx());

		// Spnego公开信息设定
		rtnMap.put("spnegoClientSecret", websiteProp.getClientSecretSpnego());

		return rtnMap;
	}
}
