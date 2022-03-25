/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service;

import java.util.List;
import java.util.Map;

/**
 * SqlSession处理Service
 *
 * @author cp0
 * @since 0.0
 */
public interface SqlSessionService {

	/**
	 * 信息取得处理
	 */
	public Map<String, Object> find(String statement);

	/**
	 * 信息取得处理
	 */
	public Map<String, Object> find(String statement, Map<String, Object> parameter);

	/**
	 * 一览取得处理
	 */
	public List<Map<String, Object>> search(String statement);

	/**
	 * 一览取得处理
	 */
	public List<Map<String, Object>> search(String statement, Map<String, Object> parameter);

	/**
	 * 插入处理
	 */
	public int insert(String statement, Map<String, Object> parameter);

	/**
	 * 批量插入处理
	 */
	public int batchInsert(String statement, List<Map<String, Object>> parameter);

	/**
	 * 更新处理
	 */
	public int update(String statement, Map<String, Object> parameter);

	/**
	 * 删除处理
	 */
	public int delete(String statement, Map<String, Object> parameter);
}
