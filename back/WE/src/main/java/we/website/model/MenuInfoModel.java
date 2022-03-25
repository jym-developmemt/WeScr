/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import java.util.List;

import we.base.base.BaseModel;

/**
 * 菜单信息模型
 *
 * @author cp0
 * @since 2.0
 */
public class MenuInfoModel extends BaseModel {

	/** 版本ID */
	private int versionId;

	/** 菜单ID */
	private String menuId;

	/** 菜单名称 */
	private String menuName;

	/** 菜单类型 */
	private String menuType;

	/** 页面组Id */
	private String groupId;

	/** 页面Id */
	private String pageId;

	/** 父菜单ID */
	private String parentId;

	/** 子节点提取 */
	private String extractFlg;

	/** 菜单图标 */
	private String icon;

	/** 权限类型 */
	private String authType;

	/** 权限值 */
	private String authValue;

	/** 附加信息 */
	private String menuAddon;

	/** 附加信息 */
	private List<MenuInfoModel> childMenuList;

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
	 * Getting method of menuId
	 *
	 * @return menuId
	 */
	public String getMenuId() {
		return menuId;
	}

	/**
	 * Setting method of menuId
	 *
	 * @param menuId
	 */
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	/**
	 * Getting method of menuName
	 *
	 * @return menuName
	 */
	public String getMenuName() {
		return menuName;
	}

	/**
	 * Setting method of menuName
	 *
	 * @param menuName
	 */
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	/**
	 * Getting method of menuType
	 *
	 * @return menuType
	 */
	public String getMenuType() {
		return menuType;
	}

	/**
	 * Setting method of menuType
	 *
	 * @param menuType
	 */
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	/**
	 * Getting method of menuType
	 *
	 * @return menuType
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
	 * Getting method of groupId
	 *
	 * @return groupId
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
	 * Getting method of parentId
	 *
	 * @return parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Setting method of parentId
	 *
	 * @param parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Getting method of extractFlg
	 *
	 * @return extractFlg
	 */
	public String getExtractFlg() {
		return extractFlg;
	}

	/**
	 * Setting method of extractFlg
	 *
	 * @param extractFlg
	 */
	public void setExtractFlg(String extractFlg) {
		this.extractFlg = extractFlg;
	}

	/**
	 * Getting method of icon
	 *
	 * @return icon
	 */
	public String getIcon() {
		return icon;
	}

	/**
	 * Setting method of icon
	 *
	 * @param icon
	 */
	public void setIcon(String icon) {
		this.icon = icon;
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
	 * Getting method of menuAddon
	 *
	 * @return menuAddon
	 */
	public String getMenuAddon() {
		return menuAddon;
	}

	/**
	 * Setting method of menuAddon
	 *
	 * @param menuAddon
	 */
	public void setMenuAddon(String menuAddon) {
		this.menuAddon = menuAddon;
	}

	/**
	 * Getting method of childMenuList
	 *
	 * @return childMenuList
	 */
	public List<MenuInfoModel> getChildMenuList() {
		return childMenuList;
	}

	/**
	 * Setting method of childMenuList
	 *
	 * @param childMenuList
	 */
	public void setChildMenuList(List<MenuInfoModel> childMenuList) {
		this.childMenuList = childMenuList;
	}

}
