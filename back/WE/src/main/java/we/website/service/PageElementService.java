/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.service;

import java.util.List;

import org.springframework.stereotype.Service;

import we.website.model.PageElementSetRowModel;

/**
 * 页面项目信息取得Service
 *
 * @author cp0
 * @since 3.0
 */
@Service
public interface PageElementService {

	/**
	 * 页面项目信息检索
	 */
	public List<PageElementSetRowModel> searchPageElementSetRow(PageElementSetRowModel elementParam) throws Exception;

}
