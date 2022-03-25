/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade;

import we.website.dto.ResourceInfoDto;

/**
 * 资源信息Facade
 *
 * @author cp0
 * @since 0.0
 */
public interface ResourceInfoOthersFacade {

	/**
	 * 资源上传
	 */
	public String upload(ResourceInfoDto resourceInfoDto) throws Exception;

}
