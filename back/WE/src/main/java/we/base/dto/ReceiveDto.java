/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.base.dto;

import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 收信Dto
 *
 * @author cp0
 * @since 0.0
 */
public class ReceiveDto {

	/** 版本ID */
	private int versionId;

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
	public void setVersionId(@RequestHeader("version-id") int versionId) {
		this.versionId = versionId;
	}
}
