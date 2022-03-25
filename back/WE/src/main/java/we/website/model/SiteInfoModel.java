/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import java.util.HashMap;
import java.util.Map;

import we.base.base.BaseModel;

/**
 * 站点信息模型
 *
 * @author cp0
 * @since 3.0
 */
public class SiteInfoModel extends BaseModel {

	/** 版本ID */
	private int versionId;

	/** 站点ID */
	private String siteId;

	/** 站点名称 */
	private String siteName;

	/** 站点子名称 */
	private String siteSubName;

	/** 站点默认语言 */
	private String localeSettingIndex;

	/** 默认页面ID */
	private String defaultPageid;

	/** 手机首页ID */
	private String mobilePageid;

	/** 消息页面ID */
	private String messagePageid;

	/** 更改密码 */
	private String changePassword;

	/** 门户菜单ID */
	private String portalMenuId;

	/** 管理菜单ID */
	private String consoleMenuId;

	/** 手机菜单ID */
	private String mobileMenuId;

	/** 页面附加信息 */
	private String addon;

	/** 外部系统信息 */
	private Map<String, Object> externalInfo = new HashMap<String, Object>();

	/**
	 * Getting method of versionId
	 *
	 * @return versionId
	 */
	public int getVersionId() {
		return versionId;
	}

	/**
	 * Setting method of versionId
	 *
	 * @param versionId
	 */
	public void setVersionId(int versionId) {
		this.versionId = versionId;
	}

	/**
	 * Getting method of siteId
	 *
	 * @return siteId
	 */
	public String getSiteId() {
		return siteId;
	}

	/**
	 * Setting method of siteId
	 *
	 * @param siteId
	 */
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	/**
	 * Getting method of siteName
	 *
	 * @return siteName
	 */
	public String getSiteName() {
		return siteName;
	}

	/**
	 * Setting method of siteName
	 *
	 * @param siteName
	 */
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	/**
	 * Getting method of siteSubName
	 *
	 * @return siteSubName
	 */
	public String getSiteSubName() {
		return siteSubName;
	}

	/**
	 * Setting method of siteSubName
	 *
	 * @param siteSubName
	 */
	public void setSiteSubName(String siteSubName) {
		this.siteSubName = siteSubName;
	}

	/**
	 * Getting method of localeSettingIndex
	 *
	 * @return localeSettingIndex
	 */
	public String getLocaleSettingIndex() {
		return localeSettingIndex;
	}

	/**
	 * Setting method of localeSettingIndex
	 *
	 * @param localeSettingIndex
	 */
	public void setLocaleSettingIndex(String localeSettingIndex) {
		this.localeSettingIndex = localeSettingIndex;
	}

	/**
	 * Getting method of defaultPageid
	 *
	 * @return defaultPageid
	 */
	public String getDefaultPageid() {
		return defaultPageid;
	}

	/**
	 * Setting method of defaultPageid
	 *
	 * @param defaultPageid
	 */
	public void setDefaultPageid(String defaultPageid) {
		this.defaultPageid = defaultPageid;
	}

	/**
	 * Getting method of mobilePageid
	 *
	 * @return mobilePageid
	 */
	public String getMobilePageid() {
		return mobilePageid;
	}

	/**
	 * Setting method of mobilePageid
	 *
	 * @param mobilePageid
	 */
	public void setMobilePageid(String mobilePageid) {
		this.mobilePageid = mobilePageid;
	}

	/**
	 * Getting method of messagePageid
	 *
	 * @return messagePageid
	 */
	public String getMessagePageid() {
		return messagePageid;
	}

	/**
	 * Setting method of messagePageid
	 *
	 * @param messagePageid
	 */
	public void setMessagePageid(String messagePageid) {
		this.messagePageid = messagePageid;
	}

	/**
	 * Getting method of changePassword
	 *
	 * @return changePassword
	 */
	public String getChangePassword() {
		return changePassword;
	}

	/**
	 * Setting method of changePassword
	 *
	 * @param changePassword
	 */
	public void setChangePassword(String changePassword) {
		this.changePassword = changePassword;
	}

	/**
	 * Getting method of portalMenuId
	 *
	 * @return portalMenuId
	 */
	public String getPortalMenuId() {
		return portalMenuId;
	}

	/**
	 * Setting method of portalMenuId
	 *
	 * @param portalMenuId
	 */
	public void setPortalMenuId(String portalMenuId) {
		this.portalMenuId = portalMenuId;
	}

	/**
	 * Getting method of consoleMenuId
	 *
	 * @return consoleMenuId
	 */
	public String getConsoleMenuId() {
		return consoleMenuId;
	}

	/**
	 * Setting method of consoleMenuId
	 *
	 * @param consoleMenuId
	 */
	public void setConsoleMenuId(String consoleMenuId) {
		this.consoleMenuId = consoleMenuId;
	}

	/**
	 * Getting method of mobileMenuId
	 *
	 * @return mobileMenuId
	 */
	public String getMobileMenuId() {
		return mobileMenuId;
	}

	/**
	 * Setting method of mobileMenuId
	 *
	 * @param mobileMenuId
	 */
	public void setMobileMenuId(String mobileMenuId) {
		this.mobileMenuId = mobileMenuId;
	}

	/**
	 * Getting method of addon
	 *
	 * @return addon
	 */
	public String getAddon() {
		return addon;
	}

	/**
	 * Setting method of addon
	 *
	 * @param addon
	 */
	public void setAddon(String addon) {
		this.addon = addon;
	}

	/**
	 * Getting method of externalInfo
	 *
	 * @return externalInfo
	 */
	public Map<String, Object> getExternalInfo() {
		return externalInfo;
	}

	/**
	 * Setting method of externalInfo
	 *
	 * @param externalInfo
	 */
	public void setExternalInfo(Map<String, Object> externalInfo) {
		this.externalInfo = externalInfo;
	}

}
