/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade;

import we.website.dto.ResourceInfoDto;
import we.website.model.ResourceInfoModel;

/**
 * 资源信息Facade
 *
 * @author cp0
 * @since 0.0
 */
public interface ResourceInfoFacade {

	/**
	 * 资源上传
	 */
	public String upload(ResourceInfoDto resourceInfoDto) throws Exception;

	/**
	 * 资源删除
	 */
	public void delete(ResourceInfoDto resourceInfoDto) throws Exception;

	/**
	 * 资源下载
	 */
	public ResourceInfoModel download(ResourceInfoDto resourceInfoDto) throws Exception;
}
