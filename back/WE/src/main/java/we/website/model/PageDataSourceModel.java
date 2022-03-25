/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import we.base.util.CommonUtil;

/**
 * 页面数据源信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class PageDataSourceModel extends DataSourceInfoModel {

	/** 页面组ID */
	@JsonIgnore
	private String groupId;

	/** 页面ID */
	@JsonIgnore
	private String pageId;

	/** 数据源番号 */
	private String datasourceIndex;

	/** 数据源类型 */
	private String datasourceType;

	/** 数据源ID */
	private String datasourceId;

	/** 初始化 */
	private String initSearchFlg;

	/** 数据类型 */
	private String searchType;

	/** 初始检索条件 */
	private String searchCondition;

	/** 每页显示数 */
	private String pageSize;

	/** 数据源附加信息 */
	private String datasourceAddon;

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
	 * Getting method of datasourceType
	 *
	 * @return datasourceType
	 */
	public String getDatasourceType() {
		return datasourceType;
	}

	/**
	 * Setting method of datasourceType
	 *
	 * @param datasourceType
	 */
	public void setDatasourceType(String datasourceType) {
		this.datasourceType = datasourceType;
	}

	/**
	 * Getting method of datasourceId
	 *
	 * @return datasourceId
	 */
	public String getDatasourceId() {
		return datasourceId;
	}

	/**
	 * Setting method of datasourceId
	 *
	 * @param datasourceId
	 */
	public void setDatasourceId(String datasourceId) {
		this.datasourceId = datasourceId;
	}

	/**
	 * Getting method of initSearchFlg
	 *
	 * @return initSearchFlg
	 */
	public String getInitSearchFlg() {
		return initSearchFlg;
	}

	/**
	 * Setting method of initSearchFlg
	 *
	 * @param initSearchFlg
	 */
	public void setInitSearchFlg(String initSearchFlg) {
		this.initSearchFlg = initSearchFlg;
	}

	/**
	 * Getting method of searchType
	 *
	 * @return searchType
	 */
	public String getSearchType() {
		return searchType;
	}

	/**
	 * Setting method of searchType
	 *
	 * @param searchType
	 */
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	/**
	 * Getting method of searchCondition
	 *
	 * @return searchCondition
	 */
	public String getSearchCondition() {
		return searchCondition;
	}

	/**
	 * Setting method of searchCondition
	 *
	 * @param searchCondition
	 */
	public void setSearchCondition(String searchCondition) {
		this.searchCondition = searchCondition;
	}

	/**
	 * Getting method of pageSize
	 *
	 * @return pageSize
	 */
	public String getPageSize() {
		return pageSize;
	}

	/**
	 * Setting method of pageSize
	 *
	 * @param pageSize
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Getting method of datasourceAddon
	 *
	 * @return datasourceAddon
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> getDatasourceAddon() throws Exception {
		Map<String, Object> addonMap = new HashMap<String, Object>();
		if (StringUtils.hasText(datasourceAddon)) {
			JSONObject addonObj = new JSONObject(datasourceAddon);
			Iterator names = addonObj.keys();
			while (names.hasNext()) {
				String name = CommonUtil.toString(names.next());
				addonMap.put(name, addonObj.get(name));
			}
		}
		return addonMap;
	}

	/**
	 * Setting method of datasourceAddon
	 *
	 * @param datasourceAddon
	 */
	public void setDatasourceAddon(String datasourceAddon) {
		this.datasourceAddon = datasourceAddon;
	}

	/**
	 * Getting method of aliasName
	 *
	 * @return aliasName
	 */
	public String aliasName() {
		return "d" + datasourceIndex;
	}
}
