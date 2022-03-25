/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dto;

import org.springframework.web.multipart.MultipartFile;

import we.base.dto.ReceiveDto;

/**
 * 资源信息Dto
 *
 * @author cp0
 * @since 0.0
 */
public class ResourceInfoDto extends ReceiveDto {

	// 资源ID
	private String resourceId;

	/** 资源名称 */
	private String resourceName;

	/** 资源类型 */
	private String resourceType = "0";

	/** 权限类型 */
	private String authType;

	/** 权限值 */
	private String authValue;

	// 资源文件
	private MultipartFile file;

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
	 * Getting method of file
	 *
	 * @return file
	 */
	public MultipartFile getFile() {
		return file;
	}

	/**
	 * Setting method of file
	 *
	 * @param file
	 */
	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
