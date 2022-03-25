/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import we.base.util.CommonUtil;

/**
 * 数据源列信息模型
 *
 * @author cp0
 * @since 3.0
 */
public class PageDataSourceColumnModel extends DataSourceColumnModel {

	/** 页面组ID */
	@JsonIgnore
	private String groupId;

	/** 页面ID */
	@JsonIgnore
	private String pageId;

	/** 数据源番号 */
	private String datasourceIndex;

	/** 列番号 */
	private String conditionType;

	/** 统计方式 */
	private String statisticsType;

	/** 默认值 */
	private String defaultValue;

	/** 排序番号 */
	private String orderIndex;

	/** 排序方法 */
	private String orderMethod;

	/** 参照键1 */
	private String referKey1;

	/** 参照键2 */
	private String referKey2;

	/** 参照键3 */
	private String referKey3;

	/** 参照键4 */
	private String referKey4;

	/** 参照键5 */
	private String referKey5;

	/** 关联列ID */
	private String linkColumnId;

	/** 关联数据源番号 */
	private String linkDatasourceIndex;

	/** 关联方式 */
	private String joinType;

	/** 关联条件 */
	private String linkCondition;

	/** 表关联条件 */
	private String linkConditionFlg;

	/** 列附加信息 */
	private String columnAddon;

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
	 * Getting method of datasourceIndex
	 *
	 * @return datasourceIndex
	 */
	public String getDatasourceIndex() {
		return datasourceIndex;
	}

	/**
	 * Setting method of datasourceIndex
	 *
	 * @param datasourceIndex
	 */
	public void setDatasourceIndex(String datasourceIndex) {
		this.datasourceIndex = datasourceIndex;
	}

	/**
	 * Getting method of conditionType
	 *
	 * @return conditionType
	 */
	public String getConditionType() {
		return conditionType;
	}

	/**
	 * Setting method of conditionType
	 *
	 * @param conditionType
	 */
	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	/**
	 * Getting method of statisticsType
	 *
	 * @return statisticsType
	 */
	public String getStatisticsType() {
		return statisticsType;
	}

	/**
	 * Setting method of statisticsType
	 *
	 * @param statisticsType
	 */
	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}

	/**
	 * Getting method of defaultValue
	 *
	 * @return defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Setting method of defaultValue
	 *
	 * @param defaultValue
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Getting method of orderIndex
	 *
	 * @return orderIndex
	 */
	public String getOrderIndex() {
		return orderIndex;
	}

	/**
	 * Setting method of orderIndex
	 *
	 * @param orderIndex
	 */
	public void setOrderIndex(String orderIndex) {
		this.orderIndex = orderIndex;
	}

	/**
	 * Getting method of orderMethod
	 *
	 * @return orderMethod
	 */
	public String getOrderMethod() {
		return orderMethod;
	}

	/**
	 * Setting method of orderMethod
	 *
	 * @param orderMethod
	 */
	public void setOrderMethod(String orderMethod) {
		this.orderMethod = orderMethod;
	}

	/**
	 * Getting method of referKey1
	 *
	 * @return referKey1
	 */
	public String getReferKey1() {
		return referKey1;
	}

	/**
	 * Setting method of referKey1
	 *
	 * @param referKey1
	 */
	public void setReferKey1(String referKey1) {
		this.referKey1 = referKey1;
	}

	/**
	 * Getting method of referKey2
	 *
	 * @return referKey2
	 */
	public String getReferKey2() {
		return referKey2;
	}

	/**
	 * Setting method of referKey2
	 *
	 * @param referKey2
	 */
	public void setReferKey2(String referKey2) {
		this.referKey2 = referKey2;
	}

	/**
	 * Getting method of referKey3
	 *
	 * @return referKey3
	 */
	public String getReferKey3() {
		return referKey3;
	}

	/**
	 * Setting method of referKey3
	 *
	 * @param referKey3
	 */
	public void setReferKey3(String referKey3) {
		this.referKey3 = referKey3;
	}

	/**
	 * Getting method of referKey4
	 *
	 * @return referKey4
	 */
	public String getReferKey4() {
		return referKey4;
	}

	/**
	 * Setting method of referKey4
	 *
	 * @param referKey4
	 */
	public void setReferKey4(String referKey4) {
		this.referKey4 = referKey4;
	}

	/**
	 * Getting method of referKey5
	 *
	 * @return referKey5
	 */
	public String getReferKey5() {
		return referKey5;
	}

	/**
	 * Setting method of referKey5
	 *
	 * @param referKey5
	 */
	public void setReferKey5(String referKey5) {
		this.referKey5 = referKey5;
	}

	/**
	 * Getting method of linkColumnId
	 *
	 * @return linkColumnId
	 */
	public String getLinkColumnId() {
		return linkColumnId;
	}

	/**
	 * Setting method of linkColumnId
	 *
	 * @param linkColumnId
	 */
	public void setLinkColumnId(String linkColumnId) {
		this.linkColumnId = linkColumnId;
	}

	/**
	 * Getting method of linkDatasourceIndex
	 *
	 * @return linkDatasourceIndex
	 */
	public String getLinkDatasourceIndex() {
		return linkDatasourceIndex;
	}

	/**
	 * Setting method of linkDatasourceIndex
	 *
	 * @param linkDatasourceIndex
	 */
	public void setLinkDatasourceIndex(String linkDatasourceIndex) {
		this.linkDatasourceIndex = linkDatasourceIndex;
	}

	/**
	 * Getting method of joinType
	 *
	 * @return joinType
	 */
	public String getJoinType() {
		return joinType;
	}

	/**
	 * Setting method of joinType
	 *
	 * @param joinType
	 */
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}

	/**
	 * Getting method of linkCondition
	 *
	 * @return linkCondition
	 */
	public String getLinkCondition() {
		return linkCondition;
	}

	/**
	 * Setting method of linkCondition
	 *
	 * @param linkCondition
	 */
	public void setLinkCondition(String linkCondition) {
		this.linkCondition = linkCondition;
	}

	/**
	 * Getting method of linkConditionFlg
	 *
	 * @return linkConditionFlg
	 */
	public String getLinkConditionFlg() {
		return linkConditionFlg;
	}

	/**
	 * Setting method of linkConditionFlg
	 *
	 * @param linkConditionFlg
	 */
	public void setLinkConditionFlg(String linkConditionFlg) {
		this.linkConditionFlg = linkConditionFlg;
	}

	/**
	 * Getting method of referKey
	 *
	 * @param index
	 * @return referKey
	 */
	public String getReferKey(String index) throws Exception {
		return CommonUtil.toString(PropertyUtils.getProperty(this, "referKey" + index));
	}

	/**
	 * Getting method of columnAddon
	 *
	 * @return columnAddon
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getColumnAddon() throws Exception {
		Map<String, Object> addonMap = new HashMap<String, Object>();
		if (StringUtils.hasText(columnAddon)) {
			JSONObject addonObj = new JSONObject(columnAddon);
			Iterator names = addonObj.keys();
			while (names.hasNext()) {
				String name = CommonUtil.toString(names.next());
				addonMap.put(name, addonObj.get(name));
			}
		}
		return addonMap;
	}

	/**
	 * Setting method of columnAddon
	 *
	 * @param columnAddon
	 */
	public void setColumnAddon(String columnAddon) {
		this.columnAddon = columnAddon;
	}

	/**
	 * Getting method of aliasName
	 *
	 * @return aliasName
	 */
	public String getAliasName() {
		return "d" + datasourceIndex;
	}

	/**
	 * Getting method of aliasName
	 *
	 * @return aliasName
	 */
	public String getConditionColumnId() {
		return this.getAliasName() + "_" + this.getColumnId();
	}
}
