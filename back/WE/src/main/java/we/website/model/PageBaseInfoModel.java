/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import we.base.base.BaseModel;

/**
 * 页面基本信息模型
 *
 * @author cp0
 * @since 3.0
 */
public class PageBaseInfoModel extends BaseModel {

	/** 页面组ID */
	private String groupId;

	/** 站点ID */
	private String pageId;

	/** 页面名称 */
	private String pageName;

	/** 页面描述 */
	private String pageDescription;

	/** 页面类型 */
	private String pageType;

	/** 权限类型 */
	private String authType;

	/** 权限值 */
	private String authValue;

	/** 页面变量 */
	private String pageVariable;

	/** 页面附加信息 */
	private String pageAddon;

	/**
	 * Getting method of groupId
	 *
	 * @return groupId
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * Setting method of groupId
	 *
	 * @param groupId
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	/**
	 * Getting method of pageId
	 *
	 * @return pageId
	 */
	public String getPageId() {
		return pageId;
	}

	/**
	 * Setting method of pageId
	 *
	 * @param pageId
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	/**
	 * Getting method of pageName
	 *
	 * @return pageName
	 */
	public String getPageName() {
		return pageName;
	}

	/**
	 * Setting method of pageName
	 *
	 * @param pageName
	 */
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * Getting method of pageDescription
	 *
	 * @return pageDescription
	 */
	public String getPageDescription() {
		return pageDescription;
	}

	/**
	 * Setting method of pageDescription
	 *
	 * @param pageDescription
	 */
	public void setPageDescription(String pageDescription) {
		this.pageDescription = pageDescription;
	}

	/**
	 * Getting method of pageType
	 *
	 * @return pageType
	 */
	public String getPageType() {
		return pageType;
	}

	/**
	 * Setting method of pageType
	 *
	 * @param pageType
	 */
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	/**
	 * Getting method of authType
	 *
	 * @return authType
	 */
	public String getAuthType() {
		return authType;
	}

	/**
	 * Setting method of authType
	 *
	 * @param authType
	 */
	public void setAuthType(String authType) {
		this.authType = authType;
	}

	/**
	 * Getting method of authValue
	 *
	 * @return authValue
	 */
	public String getAuthValue() {
		return authValue;
	}

	/**
	 * Setting method of authValue
	 *
	 * @param authValue
	 */
	public void setAuthValue(String authValue) {
		this.authValue = authValue;
	}

	/**
	 * Getting method of pageVariable
	 *
	 * @return pageVariable
	 */
	public String getPageVariable() {
		return pageVariable;
	}

	/**
	 * Setting method of pageVariable
	 *
	 * @param pageVariable
	 */
	public void setPageVariable(String pageVariable) {
		this.pageVariable = pageVariable;
	}

	/**
	 * Getting method of pageAddon
	 *
	 * @return pageAddon
	 */
	public String getPageAddon() {
		return pageAddon;
	}

	/**
	 * Setting method of pageAddon
	 *
	 * @param pageAddon
	 */
	public void setPageAddon(String pageAddon) {
		this.pageAddon = pageAddon;
	}

}
