/********************************************************************************
 * Copyright (c) 2017 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.dao;

import java.util.List;
import java.util.Map;

import we.core.model.SearchDataModel;

/**
 * 简单数据查询Dao
 *
 * @author cp0
 * @since 0.0
 */
public interface SearchDataDao {
	/**
	 * 取得查询数据map
	 */
	public Map<String, Object> getSearchData(SearchDataModel searchDataModel);

	/**
	 * 取得查询数据list
	 */
	public List<Map<String, Object>> getSearchDataList(SearchDataModel searchDataModel);

}
