/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.facade;

import java.util.Map;

import we.website.dto.SiteInfoDto;

/**
 * 站点信息Facade
 *
 * @author cp0
 * @since 3.0
 */
public interface SiteInfoFacade {

	/** 站点信息取得 */
	public Map<String, Object> findSiteInfo(SiteInfoDto processInfo) throws Exception;

	/** 消息数据取得 */
	public Map<String, String> findMessages(SiteInfoDto processInfo) throws Exception;

}
