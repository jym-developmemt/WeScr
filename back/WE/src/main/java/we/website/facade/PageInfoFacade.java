/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade;

import java.util.Map;

import we.website.dto.PageInfoDto;
import we.website.model.PageGroupInfoModel;

/**
 * 页面信息Facade
 *
 * @author cp0
 * @since 3.0
 */
public interface PageInfoFacade {

	/**
	 * 页面组信息取得
	 */
	public PageGroupInfoModel findPageGroupInfo(PageInfoDto pageInfoDto) throws Exception;

	/**
	 * 页面信息取得
	 */
	public Map<String, Object> findPageInfo(PageInfoDto pageInfoDto) throws Exception;

}
