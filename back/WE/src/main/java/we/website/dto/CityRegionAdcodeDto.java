/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dto;

import we.base.dto.ReceiveDto;

/**
 * 行政区划信息Dto
 *
 * @author cp0
 * @since 0.0
 */
public class CityRegionAdcodeDto extends ReceiveDto {

	/** 级别 */
	private String level;

	/** 父级代码 */
	private String parentCode;

	/**
	 * Getting method of level
	 *
	 * @return level
	 */
	public String getLevel() {
		return level;
	}

	/**
	 * Setting method of level
	 *
	 * @param level
	 */
	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * Getting method of parentCode
	 *
	 * @return parentCode
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * Setting method of parentCode
	 *
	 * @param parentCode
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}
