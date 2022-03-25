/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dto;

import we.base.dto.ReceiveDto;

/**
 * 站点信息Dto
 *
 * @author cp0
 * @since 0.0
 */
public class SiteInfoDto extends ReceiveDto {

	// 站点ID
	private String siteId;

	// 主言语ID
	private String localeId;

	// 副言语ID
	private String subLocaleId;

	// 菜单类型
	private String menuType;

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
	 * Getting method of localeId
	 *
	 * @return localeId
	 */
	public String getLocaleId() {
		return localeId;
	}

	/**
	 * Setting method of localeId
	 *
	 * @param localeId
	 */
	public void setLocaleId(String localeId) {
		this.localeId = localeId;
	}

	/**
	 * Getting method of subLocaleId
	 *
	 * @return subLocaleId
	 */
	public String getSubLocaleId() {
		return subLocaleId;
	}

	/**
	 * Setting method of subLocaleId
	 *
	 * @param subLocaleId
	 */
	public void setSubLocaleId(String subLocaleId) {
		this.subLocaleId = subLocaleId;
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
}
