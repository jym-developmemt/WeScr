/********************************************************************************
 * Copyright WE member
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.website.dao;

import java.util.List;

import we.website.model.PageElementBaseModel;
import we.website.model.PageElementDetailModel;
import we.website.model.PageElementSetModel;
import we.website.model.PageElementSetRowModel;

/**
 * 页面项目信息Dao
 *
 * @author cp0
 * @since 3.0
 */
public interface PageElementDao {

	/**
	 * 页面项目组信息检索
	 */
	public List<PageElementSetModel> searchPageElementSet(PageElementSetRowModel elementParam);


	/**
	 * 页面项目信息检索
	 */
	public List<PageElementBaseModel> searchPageElementBase(PageElementSetRowModel elementParam);


	/**
	 * 页面项目明细信息检索
	 */
	public List<PageElementDetailModel> searchPageElementDetail(PageElementSetRowModel elementParam);
}
