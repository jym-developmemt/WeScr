/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import we.base.base.BaseModel;

/**
 * 页面组信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class PageGroupInfoModel extends BaseModel {

	/** 页面组ID */
	private String groupId;

	/** 页面组名称 */
	private String groupName;

	/** 启动页面 */
	private String defaultPage;

	/** 页面风格 */
	private String pageStyle;

	/** 页面组类型 */
	private String groupType;

	/** 页面组变量 */
	private String groupVariable;

	/** 页面组附加信息 */
	private String groupAddon;

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
	 * Getting method of groupName
	 *
	 * @return groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * Setting method of groupName
	 *
	 * @param groupName
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * Getting method of defaultPage
	 *
	 * @return defaultPage
	 */
	public String getDefaultPage() {
		return defaultPage;
	}

	/**
	 * Setting method of defaultPage
	 *
	 * @param defaultPage
	 */
	public void setDefaultPage(String defaultPage) {
		this.defaultPage = defaultPage;
	}

	/**
	 * Getting method of pageStyle
	 *
	 * @return pageStyle
	 */
	public String getPageStyle() {
		return pageStyle;
	}

	/**
	 * Setting method of pageStyle
	 *
	 * @param pageStyle
	 */
	public void setPageStyle(String pageStyle) {
		this.pageStyle = pageStyle;
	}

	/**
	 * Getting method of groupType
	 *
	 * @return groupType
	 */
	public String getGroupType() {
		return groupType;
	}

	/**
	 * Setting method of groupType
	 *
	 * @param groupType
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * Getting method of groupVariable
	 *
	 * @return groupVariable
	 */
	public String getGroupVariable() {
		return groupVariable;
	}

	/**
	 * Setting method of groupVariable
	 *
	 * @param groupVariable
	 */
	public void setGroupVariable(String groupVariable) {
		this.groupVariable = groupVariable;
	}

	/**
	 * Getting method of groupAddon
	 *
	 * @return groupAddon
	 */
	public String getGroupAddon() {
		return groupAddon;
	}

	/**
	 * Setting method of groupAddon
	 *
	 * @param groupAddon
	 */
	public void setGroupAddon(String groupAddon) {
		this.groupAddon = groupAddon;
	}

}
