/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import we.base.base.BaseModel;

/**
 * 行政区划信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class CityRegionAdcodeModel extends BaseModel {

	/** 值 */
	private String value;

	/** 标签 */
	private String label;

	/** 等级 */
	private int level;

	/**
	 * Getting method of value
	 *
	 * @return value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Setting method of value
	 *
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Getting method of label
	 *
	 * @return label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Setting method of label
	 *
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Getting method of level
	 *
	 * @return level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Setting method of level
	 *
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

}
