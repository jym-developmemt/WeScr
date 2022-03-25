/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.model;

import we.base.base.BaseModel;

/**
 * 模型信息模型
 *
 * @author cp0
 * @since 0.0
 */
public class BpmnModel extends BaseModel {

	/** 模型ID */
	private String modelId;

	/** 模型名称 */
	private String modelName;

	/** 模型备考 */
	private String modelComment;

	/** 模型资源ID */
	private String resourceId;

	/**
	 * Getting method of modelId
	 *
	 * @return modelId
	 */
	public String getModelId() {
		return modelId;
	}

	/**
	 * Setting method of modelId
	 *
	 * @param modelId
	 */
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	/**
	 * Getting method of modelName
	 *
	 * @return modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * Setting method of modelName
	 *
	 * @param modelName
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * Getting method of modelComment
	 *
	 * @return modelComment
	 */
	public String getModelComment() {
		return modelComment;
	}

	/**
	 * Setting method of modelComment
	 *
	 * @param modelComment
	 */
	public void setModelComment(String modelComment) {
		this.modelComment = modelComment;
	}

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

}
