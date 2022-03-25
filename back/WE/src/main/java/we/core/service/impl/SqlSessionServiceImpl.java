/********************************************************************************
 * Copyright (c) 2020 JINGQIAO CO., LTD.
 * All rights reserved. Code can not be modified, copied, restributed in
 * any form without written permission from Dalian JingQiao Science and Technology Co.,Ltd.
 *******************************************************************************/
package we.core.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import we.base.base.BaseService;
import we.core.service.SqlSessionService;

/**
 * SqlSession处理Service
 *
 * @author cp0
 * @since 0.0
 */
@Service
public class SqlSessionServiceImpl extends BaseService implements SqlSessionService {

	@Autowired
	private SqlSession sqlSession;

	/**
	 * 信息取得处理
	 */
	@Override
	public Map<String, Object> find(String statement) {
		return sqlSession.selectOne(statement);
	}

	/**
	 * 信息取得处理
	 */
	@Override
	public Map<String, Object> find(String statement, Map<String, Object> parameter) {
		return sqlSession.selectOne(statement, parameter);
	}

	/**
	 * 一览取得处理
	 */
	@Override
	public List<Map<String, Object>> search(String statement) {
		return sqlSession.selectList(statement);
	}

	/**
	 * 一览取得处理
	 */
	@Override
	public List<Map<String, Object>> search(String statement, Map<String, Object> parameter) {
		return sqlSession.selectList(statement, parameter);
	}

	/**
	 * 插入处理
	 */
	@Override
	public int insert(String statement, Map<String, Object> parameter) {
		return sqlSession.insert(statement, parameter);
	}

	/**
	 * 批量插入处理
	 */
	public int batchInsert(String statement, List<Map<String, Object>> parameter) {
		return sqlSession.insert(statement, parameter);
	}

	/**
	 * 更新处理
	 */
	@Override
	public int update(String statement, Map<String, Object> parameter) {
		return sqlSession.update(statement, parameter);
	}

	/**
	 * 删除处理
	 */
	@Override
	public int delete(String statement, Map<String, Object> parameter) {
		return sqlSession.delete(statement, parameter);
	}
}
