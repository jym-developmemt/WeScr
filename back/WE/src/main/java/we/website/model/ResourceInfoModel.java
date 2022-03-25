/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import we.base.base.BaseModel;

/**
 * 资源信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class ResourceInfoModel extends BaseModel {

	/** 资源ID */
	private String resourceId;

	/** 资源名称 */
	private String resourceName;

	/** 资源类型 */
	private String resourceType;

	/** 资源地址 */
	@JsonIgnore
	private String resourcePath;

	/** 权限类型 */
	@JsonIgnore
	private String authType;

	/** 权限值 */
	@JsonIgnore
	private String authValue;

	/** 临时文件区分 */
	@JsonIgnore
	private String tempFlg = "0";

	/**
	 * Getting method of resourceId
	 *
	 * @return resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}

	/**
	 * Setting method of resourceId
	 *
	 * @param resourceId
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * Getting method of resourceName
	 *
	 * @return resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * Setting method of resourceName
	 *
	 * @param resourceName
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * Getting method of resourceType
	 *
	 * @return resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * Setting method of resourceType
	 *
	 * @param resourceType
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	/**
	 * Getting method of resourcePath
	 *
	 * @return resourcePath
	 */
	public String getResourcePath() {
		return resourcePath;
	}

	/**
	 * Setting method of resourcePath
	 *
	 * @param resourcePath
	 */
	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
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
	 * Getting method of tempFlg
	 *
	 * @return tempFlg
	 */
	public String getTempFlg() {
		return tempFlg;
	}

	/**
	 * Setting method of tempFlg
	 *
	 * @param tempFlg
	 */
	public void setTempFlg(String tempFlg) {
		this.tempFlg = tempFlg;
	}

}
